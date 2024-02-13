package pe.pcs.controlasesorados.domain.model

import pe.pcs.controlasesorados.data.entity.UsuarioEntity

data class Usuario(
    var id: Int = 0,
    var nombre: String = "",
    var email: String = "",
    var clave: String = "",
    var vigente: Int = 0
)

fun UsuarioEntity.toDomain() = Usuario(
    id = id,
    nombre = nombre,
    email = email,
    clave = clave,
    vigente = vigente
)
