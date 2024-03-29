package pe.pcs.controlasesorados.presentation.common

import android.content.Context
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pe.pcs.controlasesorados.ControlAsesoradosApp

object UtilsMessage {

    fun showAlertOk(titulo: String?, mensaje: String?, contexto: Context) {
        val builder = MaterialAlertDialogBuilder(contexto)
        builder.setMessage(mensaje)
            .setTitle(titulo)
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, _ -> dialog.cancel() }
        builder.create().show()
    }

    fun showToast(mensaje: String) {
        Toast.makeText(
            ControlAsesoradosApp.getAppContext(),
            mensaje,
            Toast.LENGTH_LONG
        ).show()
    }

}