package it.thefreak.android.interactivecyoaeditor.ui.home

import android.content.Context
import android.net.Uri
import android.os.FileObserver
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import it.thefreak.android.interactivecyoaeditor.FileSelector.getFile
import it.thefreak.android.interactivecyoaeditor.FileSelector.getTopFile
import it.thefreak.android.interactivecyoaeditor.JsonFileHandler.saveToJsonFile
import it.thefreak.android.interactivecyoaeditor.UniqueIdGenerator
import it.thefreak.android.interactivecyoaeditor.model.Adventure
import it.thefreak.android.interactivecyoaeditor.model.AdventureMeta
import java.io.File

class AdventuresRepository {

    private lateinit var observer: FileObserver
    private val folderName: String = "adventures"
    private val adventureFileName: String = "adventure"
    private val metaFileName: String = "meta"
    private fun topFolder(context: Context): File = getTopFile(context, folderName).apply{mkdir()}
    private fun idFolder(context: Context, id:String): File = getFile(topFolder(context), id)
    private fun adventureFile(context: Context, id: String): File = getFile(idFolder(context, id), adventureFileName)
    private fun metaFile(context: Context, id: String): File = getFile(idFolder(context, id), metaFileName)

    private lateinit var adventuresList: MutableLiveData<List<Pair<Uri, Uri>>>

    private fun getAllAdventureFolders(topFolder: File): List<File> {
        return topFolder.listFiles()
                ?.filterNotNull()
                ?.filter { file -> file.isDirectory }
                ?: listOf()
    }
    private fun getAllAdventures(topFolder: File): List<Pair<Uri, Uri>> {
        return getAllAdventureFolders(topFolder)
                .map { dir: File ->
                    Pair(
                            getFile(dir, adventureFileName).toUri(),
                            getFile(dir, metaFileName).toUri(),
                    )
                }
    }
    private fun initAdventuresList(ctx: Context) {
        val folder = topFolder(ctx)
        adventuresList = MutableLiveData(listOf())
        observer = object: FileObserver(folder) {
            override fun startWatching() {
                super.startWatching()
                adventuresList.postValue(getAllAdventures(folder))
            }

            override fun onEvent(p0: Int, p1: String?) {
                adventuresList.postValue(getAllAdventures(folder))
            }
        }.apply {
            startWatching();
        }
    }
    fun getAdventuresList(ctx: Context): LiveData<List<Pair<Uri, Uri>>> {
        if(!(::adventuresList.isInitialized)) {
            initAdventuresList(ctx)
        }
        return adventuresList
    }

    fun newAdventure(ctx: Context, adv: Adventure): AdventureMeta {
        val ids = getAllAdventureFolders(topFolder(ctx)).map { dir -> dir.name }
        val newId = UniqueIdGenerator.getNewId {
            !ids.contains(it)
        }
        val idFolder = idFolder(ctx, newId).apply{mkdir()}

        val advFile = File(idFolder, adventureFileName)
        val metaFile = File(idFolder, metaFileName)

        val meta = AdventureMeta(adv, advFile.toUri())
        saveToJsonFile(advFile, adv)
        saveToJsonFile(metaFile, meta)

        return meta
    }
}