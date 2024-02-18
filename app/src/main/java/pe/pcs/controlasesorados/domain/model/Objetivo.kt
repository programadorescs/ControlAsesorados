package pe.pcs.controlasesorados.domain.model

import pe.pcs.controlasesorados.data.entity.ObjetivoEntity

data class Objetivo(
    var id: Int = 0,
    var fecha: String = "",
    var descripcion: String = "",
    var obs: String = "",
    var estado: String = "",
    var idAsesorado: Int = 0
)

fun ObjetivoEntity.toDomain() = Objetivo(
    id = id,
    fecha = fecha,
    descripcion = descripcion,
    obs = obs,
    estado = estado,
    idAsesorado = idasesorado
)