package pe.pcs.controlasesorados.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.pcs.controlasesorados.data.dao.ObjetivoDao
import pe.pcs.controlasesorados.data.entity.toDatabase
import pe.pcs.controlasesorados.domain.model.Objetivo
import pe.pcs.controlasesorados.domain.model.toDomain
import pe.pcs.controlasesorados.domain.repository.ObjetivoRepository
import javax.inject.Inject

class ObjetivoRepositoryImpl @Inject constructor(
    private val dao: ObjetivoDao
) : ObjetivoRepository {
    override fun listar(dato: String): Flow<List<Objetivo>> {
        return dao.listar(dato).map {
            it.map { entity ->
                entity.toDomain()
            }
        }
    }

    override fun obtenerObjetivoPorAsesorado(idAsesorado: Int): Flow<List<Objetivo>> {
        return dao.obtenerObjetivoPorAsesorado(idAsesorado).map {
            it.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun grabar(entity: Objetivo): Int {
        return if (entity.id == 0)
            dao.insertar(entity.toDatabase()).toInt()
        else
            dao.actualizar(entity.toDatabase())
    }

    override suspend fun eliminar(entity: Objetivo): Int {
        return dao.eliminar(entity.toDatabase())
    }

    override suspend fun finalizarObjetivo(idObjetivo: Int): Int {
        return dao.finalizarObjetivo(idObjetivo)
    }
}