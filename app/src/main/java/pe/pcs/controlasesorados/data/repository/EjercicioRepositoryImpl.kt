package pe.pcs.controlasesorados.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.pcs.controlasesorados.data.dao.EjercicioDao
import pe.pcs.controlasesorados.data.entity.toDatabase
import pe.pcs.controlasesorados.domain.model.Ejercicio
import pe.pcs.controlasesorados.domain.model.report.ReporteEjercicioModel
import pe.pcs.controlasesorados.domain.model.report.toDomain
import pe.pcs.controlasesorados.domain.repository.EjercicioRepository
import javax.inject.Inject

class EjercicioRepositoryImpl @Inject constructor(private val dao: EjercicioDao) :
    EjercicioRepository {
    override fun listar(dato: String): Flow<List<ReporteEjercicioModel>> {
        return dao.listar(dato).map {
            it.map { report ->
                report.toDomain()
            }
        }
    }

    override suspend fun obtenerPorId(id: Int): ReporteEjercicioModel? {
        return dao.obtenerPorId(id)?.toDomain()
    }

    override suspend fun grabar(entity: Ejercicio): Int {
        return if (entity.id == 0)
            dao.insertar(entity.toDatabase()).toInt()
        else
            dao.actualizar(entity.toDatabase())
    }

    override suspend fun eliminar(entity: Ejercicio): Int {
        return dao.eliminar(entity.toDatabase())
    }
}