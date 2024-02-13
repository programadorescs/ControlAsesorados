package pe.pcs.controlasesorados.domain.repository

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Rutina

interface RutinaRepository {
    fun listar(dato: String): Flow<List<Rutina>>

    suspend fun obtenerPorId(id: Int): Rutina?

    suspend fun grabar(entity: Rutina): Int

    suspend fun eliminar(entity: Rutina): Int
}