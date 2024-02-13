package pe.pcs.controlasesorados.presentation.common

import android.content.Context
import pe.pcs.controlasesorados.ControlAsesoradosApp

object PreferencesProvider {

    fun setUsuarioLogin(correoUsuario: String) {
        val editor = ControlAsesoradosApp.getAppContext()
            .getSharedPreferences(Constants.PREFS_APP, Context.MODE_PRIVATE).edit()

        editor.putString(
            PreferencesKey.USER_LOGIN.name,
            correoUsuario.trim()
        )
        editor.apply()
    }

    fun getPreferencia(key: PreferencesKey): String? {
        return ControlAsesoradosApp.getAppContext().getSharedPreferences(
            Constants.PREFS_APP,
            Context.MODE_PRIVATE
        ).getString(key.name, "")
    }

}