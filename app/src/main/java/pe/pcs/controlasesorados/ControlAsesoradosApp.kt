package pe.pcs.controlasesorados

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ControlAsesoradosApp: Application() {
    companion object {
        private var instancia: ControlAsesoradosApp? = null

        fun getAppContext(): Context {
            return instancia!!.applicationContext
        }
    }

    init {
        instancia = this
    }

    override fun onCreate() {
        super.onCreate()

        // AdMob
        //MobileAds.initialize(this)
    }
}