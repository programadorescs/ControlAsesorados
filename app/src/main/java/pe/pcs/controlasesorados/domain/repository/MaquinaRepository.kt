package pe.pcs.controlasesorados.domain.repository

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Maquina

interface MaquinaRepository {
    fun listar(dato: String): Flow<List<Maquina>>

    suspend fun obtenerPorId(id: Int): Maquina?

    suspend fun grabar(entity: Maquina): Int

    suspend fun eliminar(entity: Maquina): Int
}