package ucne.edu.todo.ui.screen.tarea

import ucne.edu.todo.data.remote.dto.TareaDto

data class TareaListState(
    val isLoading: Boolean = false,
    val tarea:  List<TareaDto> = emptyList(),
    val error: String = ""
)