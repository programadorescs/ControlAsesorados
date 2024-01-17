package pe.pcs.controlasesorados.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.data.entity.MaquinaEntity

@Dao
interface MaquinaDao {

    @Query("SELECT id, descripcion FROM maquina WHERE descripcion LIKE '%' || :dato || '%'")
    fun listar(dato: String): Flow<List<MaquinaEntity>>

    @Query("SELECT id, descripcion FROM maquina WHERE id = :dato")
    fun obtenerPorId(dato: Int): MaquinaEntity?

    @Insert
    suspend fun insertar(entidad: MaquinaEntity): Long

    @Update
    suspend fun actualizar(entidad: MaquinaEntity): Int

    @Delete
    suspend fun eliminar(entidad: MaquinaEntity): Int

}