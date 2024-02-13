package pe.pcs.controlasesorados.domain.repository

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Usuario

interface UsuarioRepository {
    suspend fun grabar(entidad: Usuario): Int

    suspend fun eliminar(entidad: Usuario): Int

    suspend fun actualizarClave(id: Int, nuevaClave: String): Int

    suspend fun obtenerUsuario(email: String, clave: String): Usuario?

    suspend fun obtenerUsuarioPorId(id: Int): Usuario?

    suspend fun existeCuenta(): Int

    fun listar(dato: String): Flow<List<Usuario>>

    suspend fun grabarCuenta(entidad: Usuario): Usuario?
}