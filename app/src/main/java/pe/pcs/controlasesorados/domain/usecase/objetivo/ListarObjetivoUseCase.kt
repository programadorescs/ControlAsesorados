package pe.pcs.controlasesorados.domain.usecase.objetivo

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.Objetivo
import pe.pcs.controlasesorados.domain.repository.ObjetivoRepository
import javax.inject.Inject

class ListarObjetivoUseCase @Inject constructor(private val repository: ObjetivoRepository) {

    operator fun invoke(dato: String): Flow<List<Objetivo>> {
        return repository.listar(dato)
    }

}