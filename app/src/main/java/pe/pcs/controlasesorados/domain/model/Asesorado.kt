package pe.pcs.controlasesorados.domain.model

import pe.pcs.controlasesorados.data.entity.AsesoradoEntity

data class Asesorado(
    var id: Int = 0,
    var nombre: String = "",
    var dni: String = "",
    var fecnac: String = "",
    var sexo: String = "",
    var direccion: String = "",
    var telefono: String = ""
)

fun AsesoradoEntity.toDomain() = Asesorado(
    id = id,
    nombre = nombre,
    dni = dni,
    fecnac = fecnac,
    sexo = sexo,
    direccion = direccion,
    telefono = telefono
)