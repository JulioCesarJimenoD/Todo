package ucne.edu.todo.utils

sealed class Estados<out T>{
    object Idle : Estados <Nothing>()
    object Cargando: Estados <Nothing>()
    data class Exito <T> (val data: T) : Estados<T>()
    data class Error (val error: Throwable) : Estados<Nothing>()
}
