package pe.pcs.controlasesorados.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.pcs.controlasesorados.data.dao.RutinaDao
import pe.pcs.controlasesorados.data.entity.toDatabase
import pe.pcs.controlasesorados.domain.model.Rutina
import pe.pcs.controlasesorados.domain.model.toDomain
import pe.pcs.controlasesorados.domain.repository.RutinaRepository
import javax.inject.Inject

class RutinaRepositoryImpl @Inject constructor(private val dao: RutinaDao) : RutinaRepository {
    override fun listar(dato: String): Flow<List<Rutina>> {
        return dao.listar(dato).map {
            it.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun obtenerPorId(id: Int): Rutina? {
        return dao.obtenerPorId(id)?.toDomain()
    }

    override suspend fun grabar(entity: Rutina): Int {
        return if (entity.id == 0)
            dao.insertar(entity.toDatabase()).toInt()
        else
            dao.actualizar(entity.toDatabase())
    }

    override suspend fun eliminar(entity: Rutina): Int {
        return dao.eliminar(entity.toDatabase())
    }
}