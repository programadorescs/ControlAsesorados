package pe.pcs.controlasesorados.domain.usecase.ejercicio

import pe.pcs.controlasesorados.domain.model.report.ReporteEjercicioModel
import pe.pcs.controlasesorados.domain.repository.EjercicioRepository
import javax.inject.Inject

class ObtenerEjercicioUseCase @Inject constructor(private val repository: EjercicioRepository) {
    suspend operator fun invoke(id: Int): ReporteEjercicioModel? {
        return repository.obtenerPorId(id)
    }
}