package pe.pcs.controlasesorados.domain.usecase.ejercicio

import pe.pcs.controlasesorados.domain.model.Ejercicio
import pe.pcs.controlasesorados.domain.repository.EjercicioRepository
import javax.inject.Inject

class EliminarEjercicioUseCase @Inject constructor(private val repository: EjercicioRepository) {
    suspend operator fun invoke(model: Ejercicio): Int {
        return repository.eliminar(model)
    }
}