package pe.pcs.controlasesorados.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.data.entity.RutinaEntity

@Dao
interface RutinaDao {

    @Query("SELECT id, descripcion FROM rutina WHERE descripcion LIKE '%' || :dato || '%'")
    fun listar(dato: String): Flow<List<RutinaEntity>>

    @Query("SELECT id, descripcion FROM rutina WHERE id = :dato")
    fun obtenerPorId(dato: Int): RutinaEntity?

    @Insert
    suspend fun insertar(entidad: RutinaEntity): Long

    @Update
    suspend fun actualizar(entidad: RutinaEntity): Int

    @Delete
    suspend fun eliminar(entidad: RutinaEntity): Int

}