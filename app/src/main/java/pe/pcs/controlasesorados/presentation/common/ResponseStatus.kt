package pe.pcs.controlasesorados.presentation.common

// <T> funciona para cualquier tipo de datos que metamos aqui
// Esto es una clase generica
sealed class ResponseStatus<T> {
    class Success<T>(val data: T): ResponseStatus<T>()
    class Loading<T>: ResponseStatus<T>()
    class Error<T>(val message: String): ResponseStatus<T>()
}