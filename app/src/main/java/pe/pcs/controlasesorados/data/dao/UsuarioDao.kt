package pe.pcs.controlasesorados.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.data.entity.UsuarioEntity

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertar(usuario: UsuarioEntity): Long

    @Update
    suspend fun actualizar(usuario: UsuarioEntity): Int

    @Delete
    suspend fun eliminar(usuario: UsuarioEntity): Int

    @Query("SELECT id, nombre, email, clave, vigente FROM usuario WHERE nombre LIKE '%' || :dato || '%' ORDER BY nombre ASC")
    fun listarPorNombre(dato: String): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM usuario WHERE vigente=1 AND email=:email AND clave=:clave")
    suspend fun obtenerUsuario(email: String, clave: String): UsuarioEntity?

    @Query("SELECT * FROM usuario WHERE id=:id")
    suspend fun obtenerUsuarioPorId(id: Int): UsuarioEntity?

    @Query("SELECT ifnull(count(id), 0) FROM usuario")
    suspend fun existeCuenta(): Int

    @Query("UPDATE Usuario SET clave=:clave WHERE id=:id")
    suspend fun actualizarClave(id: Int, clave: String): Int

    @Transaction
    suspend fun grabarCuenta(entidad: UsuarioEntity): UsuarioEntity? {
        return obtenerUsuarioPorId(insertar(entidad).toInt())
    }
}