package pe.pcs.controlasesorados.data.entity.report

import pe.pcs.controlasesorados.domain.model.report.ReporteEjercicioModel

data class ReporteEjercicioEntity(
    var id: Int = 0,
    var descripcion: String = "",
    var idrutina: Int = 0,
    var rutina: String = "",
    var idmaquina: Int = 0,
    var maquina: String = ""
)

fun ReporteEjercicioModel.toDatabase() = ReporteEjercicioEntity(
    id = id,
    descripcion = descripcion,
    idrutina = idrutina,
    rutina = rutina,
    idmaquina = idmaquina,
    maquina = maquina
)