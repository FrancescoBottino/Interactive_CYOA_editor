package it.thefreak.android.interactivecyoaeditor.ui.home

import android.content.Context
import android.net.Uri
import android.os.FileObserver
import androidx.annotation.WorkerThread
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.thefreak.android.interactivecyoaeditor.model.entities.Adventure
import it.thefreak.android.interactivecyoaeditor.model.entities.AdventureMeta
import it.thefreak.android.interactivecyoaeditor.model.format
import it.thefreak.android.interactivecyoaeditor.utils.JsonFileHandler
import it.thefreak.android.interactivecyoaeditor.utils.UniqueIdGenerator
import it.thefreak.android.interactivecyoaeditor.utils.deserializationExcluderGson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class LocalAdventuresViewModel: ViewModel() {
    companion object {
        const val ADVENTURES_FOLDER = "/adventures"
        const val ADVENTURE_FILE_ID_NAME_LENGTH = 12
    }

    private fun getAdventuresFolder(ctx: Context): File {
        return ctx.getExternalFilesDir(ADVENTURES_FOLDER)?.also {
            it.mkdir()
        } ?: throw Exception("Couldn't get adventures directory")
    }

    private val localAdventures: MutableLiveData<List<AdventureMeta>> by lazy {
        MutableLiveData<List<AdventureMeta>>()
    }

    private var observer: FileObserver? = null

    @WorkerThread
    private suspend fun getAllAdventures(ctx: Context): List<AdventureMeta> {
        return getAdventuresFolder(ctx).listFiles()?.filter { file ->
            file.extension == "json"
        }?.map { advFile ->
            deserializationExcluderGson.fromJson(
                BufferedReader(FileReader(advFile)),
                AdventureMeta::class.java
            ).apply {
                uri = advFile.toUri()
            }
        }?.apply {
            var currentTotal = size
            filter {
                it.ordinal == null
            }.forEach {
                it.ordinal = ++currentTotal
            }
        } ?: emptyList()
    }

    fun getAdventures(ctx: Context): LiveData<List<AdventureMeta>> {
        if(observer == null) {
            viewModelScope.launch(Dispatchers.IO) {
                localAdventures.postValue(getAllAdventures(ctx))
            }
        }
        return localAdventures
    }

    fun saveNewAdventure(ctx: Context, adv: Adventure): AdventureMeta {
        val folder = getAdventuresFolder(ctx)
        val filename = UniqueIdGenerator.getNewId(ADVENTURE_FILE_ID_NAME_LENGTH) { newId ->
            !File(folder, newId).exists()
        }
        val advFile = File(folder, "$filename.json")

        viewModelScope.launch(Dispatchers.IO) {
            JsonFileHandler.saveToJsonFile(advFile, adv, format)
        }

        return adv.getMetadata().apply {
            uri = advFile.toUri()
        }
    }

    fun copyAdventure(ctx: Context, adventureMeta: AdventureMeta): AdventureMeta {
        val advUri = adventureMeta.uri!!
        val folder = getAdventuresFolder(ctx)
        val filename = UniqueIdGenerator.getNewId(ADVENTURE_FILE_ID_NAME_LENGTH) { newId ->
            !File(folder, newId).exists()
        }

        val newAdvFile = File(folder, filename)
        val oldAdvFile = File(advUri.path!!)

        if(!oldAdvFile.exists())
            throw Exception("Trying to copy a non existing adventure on path ${advUri.path!!}")

        viewModelScope.launch(Dispatchers.IO) {
            oldAdvFile.copyTo(newAdvFile)
        }

        return adventureMeta.copy(uri = newAdvFile.toUri())
    }

    fun deleteAdventure(advUri: Uri) {
        val advFile = File(advUri.path!!)

        if(!advFile.exists())
            throw Exception("Trying to delete a non existing adventure on path ${advUri.path!!}")

        viewModelScope.launch(Dispatchers.IO) {
            advFile.delete()
        }
    }
}