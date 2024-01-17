package pe.pcs.controlasesorados.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.data.entity.EjercicioEntity
import pe.pcs.controlasesorados.data.entity.ReporteEjercicio

@Dao
interface EjercicioDao {

    @Transaction
    @Query(
        "SELECT maquina.id as idmaquina, maquina.descripcion as maquina, " +
                "rutina.id as idrutina, rutina.descripcion as rutina, " +
                "ejercicio.descripcion, ejercicio.id " +
                "FROM maquina inner join ejercicio ON maquina.id = ejercicio.idmaquina " +
                "inner join rutina ON rutina.id = ejercicio.idrutina " +
                "WHERE ejercicio.descripcion LIKE '%' || :dato || '%'"
    )
    fun listar(dato: String): Flow<List<ReporteEjercicio>>

    @Insert
    suspend fun insertar(entidad: EjercicioEntity): Long

    @Update
    suspend fun actualizar(entidad: EjercicioEntity): Int

    @Delete
    suspend fun eliminar(entidad: EjercicioEntity): Int

}