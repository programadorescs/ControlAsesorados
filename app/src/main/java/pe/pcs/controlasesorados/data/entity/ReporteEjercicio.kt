package pe.pcs.controlasesorados.data.entity

data class ReporteEjercicio(
    var id: Int = 0,
    var descripcion: String = "",
    var idrutina: Int = 0,
    var rutina: String = "",
    var idmaquina: Int = 0,
    var maquina: String = ""
)
