package pe.pcs.controlasesorados.presentation.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import pe.pcs.controlasesorados.ControlAsesoradosApp
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

object UtilsCommon {

    fun ocultarTeclado(view: View) {
        val imm = ControlAsesoradosApp.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun limpiarEditText(view: View) {
        val it: Iterator<View> = view.touchables.iterator()
        while (it.hasNext()) {
            val v = it.next()
            if (v is EditText) v.setText("")
        }
    }

    fun formatearDoubleString(valor: Double): String {
        val formato = DecimalFormat("#0.00")

        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = '.'

        formato.decimalFormatSymbols = dfs

        return formato.format(valor).toString()
    }

}