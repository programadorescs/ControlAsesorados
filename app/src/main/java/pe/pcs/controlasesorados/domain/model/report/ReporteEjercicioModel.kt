package pe.pcs.controlasesorados.domain.model.report

import pe.pcs.controlasesorados.data.entity.report.ReporteEjercicioEntity

data class ReporteEjercicioModel(
    var id: Int = 0,
    var descripcion: String = "",
    var idrutina: Int = 0,
    var rutina: String = "",
    var idmaquina: Int = 0,
    var maquina: String = ""
)

fun ReporteEjercicioEntity.toDomain() = ReporteEjercicioModel(
    id = id,
    descripcion = descripcion,
    idrutina = idrutina,
    rutina = rutina,
    idmaquina = idmaquina,
    maquina = maquina
)