package pe.pcs.controlasesorados.domain.usecase.objetivo

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Objetivo
import pe.pcs.controlasesorados.domain.repository.ObjetivoRepository
import javax.inject.Inject

class ObtenerObjetivoAsesoradoUseCase @Inject constructor(private val repository: ObjetivoRepository) {

    operator fun invoke(idAsesorado: Int): Flow<List<Objetivo>> {
        return repository.obtenerObjetivoPorAsesorado(idAsesorado)
    }

}