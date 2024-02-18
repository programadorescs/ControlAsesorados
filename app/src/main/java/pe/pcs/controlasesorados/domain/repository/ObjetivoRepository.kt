package pe.pcs.controlasesorados.domain.repository

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Objetivo

interface ObjetivoRepository {

    fun listar(dato: String): Flow<List<Objetivo>>

    fun obtenerObjetivoPorAsesorado(idAsesorado: Int): Flow<List<Objetivo>>

    suspend fun grabar(entity: Objetivo): Int

    suspend fun eliminar(entity: Objetivo): Int

    suspend fun finalizarObjetivo(idObjetivo: Int): Int

}