package pe.pcs.controlasesorados.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.pcs.controlasesorados.data.dao.AsesoradoDao
import pe.pcs.controlasesorados.data.entity.toDatabase
import pe.pcs.controlasesorados.domain.model.Asesorado
import pe.pcs.controlasesorados.domain.model.toDomain
import pe.pcs.controlasesorados.domain.repository.AsesoradoRepository
import javax.inject.Inject

class AsesoradoRepositoryImpl @Inject constructor(private val dao: AsesoradoDao): AsesoradoRepository {
    override fun listar(dato: String): Flow<List<Asesorado>> {
        return dao.listar(dato).map {
            it.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun obtenerPorId(id: Int): Asesorado? {
        return dao.obtenerPorId(id)?.toDomain()
    }

    override suspend fun grabar(entity: Asesorado): Int {
        return if (entity.id == 0)
            dao.insertar(entity.toDatabase()).toInt()
        else
            dao.actualizar(entity.toDatabase())
    }

    override suspend fun eliminar(entity: Asesorado): Int {
        return dao.eliminar(entity.toDatabase())
    }
}