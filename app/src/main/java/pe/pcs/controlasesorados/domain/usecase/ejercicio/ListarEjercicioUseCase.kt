package pe.pcs.controlasesorados.domain.usecase.ejercicio

import kotlinx.coroutines.flow.Flow
import pe.pcs.controlasesorados.domain.model.report.ReporteEjercicioModel
import pe.pcs.controlasesorados.domain.repository.EjercicioRepository
import javax.inject.Inject

class ListarEjercicioUseCase @Inject constructor(private val repository: EjercicioRepository) {
    operator fun invoke(dato: String): Flow<List<ReporteEjercicioModel>> {
        return repository.listar(dato)
    }
}