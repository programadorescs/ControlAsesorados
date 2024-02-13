package pe.pcs.controlasesorados.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.pcs.controlasesorados.data.dao.UsuarioDao
import pe.pcs.controlasesorados.data.entity.toDatabase
import pe.pcs.controlasesorados.domain.model.Usuario
import pe.pcs.controlasesorados.domain.model.toDomain
import pe.pcs.controlasesorados.domain.repository.UsuarioRepository
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val dao: UsuarioDao
): UsuarioRepository {
    override suspend fun grabar(entidad: Usuario): Int {
        return if (entidad.id == 0)
            dao.insertar(entidad.toDatabase()).toInt()
        else
            dao.actualizar(entidad.toDatabase())
    }

    override suspend fun eliminar(entidad: Usuario): Int {
        return dao.eliminar(entidad.toDatabase())
    }

    override suspend fun actualizarClave(id: Int, nuevaClave: String): Int {
        return dao.actualizarClave(id, nuevaClave)
    }

    override suspend fun obtenerUsuario(email: String, clave: String): Usuario? {
        return dao.obtenerUsuario(email, clave)?.toDomain()
    }

    override suspend fun obtenerUsuarioPorId(id: Int): Usuario? {
        return dao.obtenerUsuarioPorId(id)?.toDomain()
    }

    override suspend fun existeCuenta(): Int {
        return dao.existeCuenta()
    }

    override fun listar(dato: String): Flow<List<Usuario>> {
        return dao.listarPorNombre(dato).map {
            it.map { usuarioEntity ->
                usuarioEntity.toDomain()
            }
        }
    }

    override suspend fun grabarCuenta(entidad: Usuario): Usuario? {
        return dao.grabarCuenta(entidad.toDatabase())?.toDomain()
    }
}