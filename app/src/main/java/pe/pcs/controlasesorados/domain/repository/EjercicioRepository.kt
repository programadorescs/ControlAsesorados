package pe.pcs.controlasesorados.domain.repository

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Ejercicio
import pe.pcs.controlasesorados.domain.model.report.ReporteEjercicioModel

interface EjercicioRepository {
    fun listar(dato: String): Flow<List<ReporteEjercicioModel>>

    suspend fun obtenerPorId(id: Int): ReporteEjercicioModel?

    suspend fun grabar(entity: Ejercicio): Int

    suspend fun eliminar(entity: Ejercicio): Int
}