package pe.pcs.controlasesorados.domain.model

import pe.pcs.controlasesorados.data.entity.EjercicioEntity

data class Ejercicio(
    var id: Int = 0,
    var descripcion: String = "",
    var idrutina: Int = 0,
    var idmaquina: Int = 0,
)

fun EjercicioEntity.toDomain() = Ejercicio(
    id = id,
    descripcion = descripcion,
    idrutina = idrutina,
    idmaquina = idmaquina
)