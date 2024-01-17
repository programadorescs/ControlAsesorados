package pe.pcs.controlasesorados.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.data.entity.ObjetivoEntity

@Dao
interface ObjetivoDao {

    @Query("SELECT id, fecha, descripcion, obs, estado, idasesorado FROM objetivo WHERE descripcion LIKE '%' || :dato || '%' Order By id DESC")
    fun listar(dato: String): Flow<List<ObjetivoEntity>>

    @Insert
    suspend fun insertar(entidad: ObjetivoEntity): Long

    @Update
    suspend fun actualizar(entidad: ObjetivoEntity): Int

    @Delete
    suspend fun eliminar(entidad: ObjetivoEntity): Int

}