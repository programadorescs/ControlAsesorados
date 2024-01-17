package pe.pcs.controlasesorados.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.data.entity.AsesoradoEntity

@Dao
interface AsesoradoDao {

    @Query("SELECT id, nombre, dni, fecnac, direccion, telefono, sexo FROM asesorado WHERE nombre LIKE '%' || :dato || '%'")
    fun listar(dato: String): Flow<List<AsesoradoEntity>>

    @Insert
    suspend fun insertar(entidad: AsesoradoEntity): Long

    @Update
    suspend fun actualizar(entidad: AsesoradoEntity): Int

    @Delete
    suspend fun eliminar(entidad: AsesoradoEntity): Int

}