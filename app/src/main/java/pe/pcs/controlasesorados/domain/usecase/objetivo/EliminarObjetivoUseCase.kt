package pe.pcs.controlasesorados.domain.usecase.objetivo

import pe.pcs.controlasesorados.domain.model.Objetivo
import pe.pcs.controlasesorados.domain.repository.ObjetivoRepository
import javax.inject.Inject

class EliminarObjetivoUseCase @Inject constructor(private val repository: ObjetivoRepository) {

    suspend operator fun invoke(entidad: Objetivo): Int {
        return repository.eliminar(entidad)
    }

}