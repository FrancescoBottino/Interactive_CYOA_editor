package it.thefreak.android.interactivecyoaeditor

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
abstract class BasicActivity : AppCompatActivity() {
    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = this.applicationContext as App
    }

    override fun onResume() {
        super.onResume()
        app.currentActivity = this
    }

    override fun onPause() {
        clearReferences()
        super.onPause()
    }

    override fun onDestroy() {
        clearReferences()
        super.onDestroy()
    }

    private fun clearReferences() {
        val currActivity: BasicActivity? = app.currentActivity
        if (this == currActivity) app.currentActivity = null
    }
}