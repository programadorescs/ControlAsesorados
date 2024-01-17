package pe.pcs.controlasesorados.domain.model

import pe.pcs.controlasesorados.data.entity.MaquinaEntity

data class Maquina(
    var id: Int = 0,
    var descripcion: String = ""
)

fun MaquinaEntity.toDomain() = Maquina(
    id = id,
    descripcion = descripcion
)