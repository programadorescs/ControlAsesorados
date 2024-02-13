package pe.pcs.controlasesorados.domain.model

import pe.pcs.controlasesorados.data.entity.RutinaEntity

data class Rutina(
    var id: Int = 0,
    var descripcion: String = ""
)

fun RutinaEntity.toDomain() = Rutina(
    id = id,
    descripcion = descripcion
)