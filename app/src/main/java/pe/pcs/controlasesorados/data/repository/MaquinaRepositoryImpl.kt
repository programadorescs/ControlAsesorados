package pe.pcs.controlasesorados.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pe.pcs.controlasesorados.data.dao.MaquinaDao
import pe.pcs.controlasesorados.data.entity.toDatabase
import pe.pcs.controlasesorados.domain.model.Maquina
import pe.pcs.controlasesorados.domain.model.toDomain
import pe.pcs.controlasesorados.domain.repository.MaquinaRepository
import javax.inject.Inject

class MaquinaRepositoryImpl @Inject constructor(private val dao: MaquinaDao): MaquinaRepository {

    override fun listar(dato: String): Flow<List<Maquina>> {
        return dao.listar(dato).map {
            it.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun obtenerPorId(id: Int): Maquina? {
        return dao.obtenerPorId(id)?.toDomain()
    }

    override suspend fun grabar(entity: Maquina): Int {
        return if (entity.id == 0)
            dao.insertar(entity.toDatabase()).toInt()
        else
            dao.actualizar(entity.toDatabase())
    }

    override suspend fun eliminar(entity: Maquina): Int {
        return dao.eliminar(entity.toDatabase())
    }

}