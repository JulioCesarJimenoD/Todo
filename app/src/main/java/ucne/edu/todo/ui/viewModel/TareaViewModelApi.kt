package ucne.edu.todo.ui.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ucne.edu.todo.data.remote.dto.TareaDto
import ucne.edu.todo.data.repositories.Api_Repository.TareaApiRepository
import ucne.edu.todo.data.repositories.TareaRepository
import ucne.edu.todo.ui.screen.tarea.TareaListState
import ucne.edu.todo.utils.Resource
import javax.inject.Inject

class TareaViewModelApi @Inject constructor(
    private val tareaApiRepository: TareaApiRepository
): ViewModel()
{
    var NombreTarea by mutableStateOf("")
    var Descripcion by mutableStateOf("")
    var Prioridad by mutableStateOf("")

    private var _state = mutableStateOf(TareaListState())
    val state: State<TareaListState> = _state

    init {
        tareaApiRepository.getTarea().onEach{result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = TareaListState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = TareaListState(tarea = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = TareaListState(error = result.message ?: "Error desconocido")
                }
            }
        }.launchIn(viewModelScope)
    }
    fun Guardar(){
        viewModelScope.launch {
            tareaApiRepository.postAgenda(
                TareaDto(
                   tareaId = 0,
                    nombre = NombreTarea,
                    descripcion = Descripcion,
                    prioridad = Prioridad
                )
            )
        }
    }
}