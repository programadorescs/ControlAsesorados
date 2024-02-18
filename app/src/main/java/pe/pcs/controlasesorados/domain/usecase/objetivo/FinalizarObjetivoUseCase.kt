package pe.pcs.controlasesorados.domain.usecase.objetivo

import pe.pcs.controlasesorados.domain.repository.ObjetivoRepository
import javax.inject.Inject

class FinalizarObjetivoUseCase @Inject constructor(private val repository: ObjetivoRepository) {

    suspend operator fun invoke(idObjetivo: Int): Int {
        return repository.finalizarObjetivo(idObjetivo)
    }

}