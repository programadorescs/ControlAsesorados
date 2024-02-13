package pe.pcs.controlasesorados.domain.repository

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Asesorado

interface AsesoradoRepository {

    fun listar(dato: String): Flow<List<Asesorado>>

    suspend fun obtenerPorId(id: Int): Asesorado?

    suspend fun grabar(entity: Asesorado): Int

    suspend fun eliminar(entity: Asesorado): Int

}