package ucne.edu.todo.ui.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ucne.edu.todo.data.models.Prioridad
import ucne.edu.todo.data.models.Tarea
import ucne.edu.todo.data.remote.dto.TareaDto
import ucne.edu.todo.data.repositories.Api_Repository.TareaApiRepository
import ucne.edu.todo.data.repositories.DataRepository
import ucne.edu.todo.data.repositories.TareaRepository
import ucne.edu.todo.utils.Action
import ucne.edu.todo.utils.BuscarAppEstados
import ucne.edu.todo.utils.Constante.MAX_TITLE_LENGTH
import ucne.edu.todo.utils.Estados
import javax.inject.Inject

@HiltViewModel
class ShviewModel @Inject constructor(
    private val repository: TareaRepository,
    private val dataRepository: DataRepository,
    private val api: TareaApiRepository
) : ViewModel()
{
    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id: MutableState<Int> = mutableStateOf(0)
    val nombre : MutableState<String> = mutableStateOf("")
    val descripcion: MutableState<String> = mutableStateOf("")
    val prioridad: MutableState<Prioridad> = mutableStateOf(Prioridad.BAJO)

    val buscarAppEstados: MutableState<BuscarAppEstados> =
        mutableStateOf(BuscarAppEstados.CLOSED)

    val buscarTextEstados: MutableState<String> = mutableStateOf("")

    private val _allTareas =
        MutableStateFlow<Estados<List<Tarea>>>(Estados.Idle)
    val allTareas: StateFlow<Estados<List<Tarea>>> = _allTareas

    private val _searchTareas =
        MutableStateFlow<Estados<List<Tarea>>>(Estados.Idle)

    val searchedTareas: StateFlow<Estados<List<Tarea>>> = _searchTareas

    private val _sortEstados=
        MutableStateFlow<Estados<Prioridad>>(Estados.Idle)
    val sortEstados: StateFlow<Estados<Prioridad>> = _sortEstados

    private val _selecionTareas: MutableStateFlow<Tarea?> = MutableStateFlow(null)
    val selectedTarea: StateFlow<Tarea?> = _selecionTareas

    val Prioridadbaja: StateFlow<List<Tarea>> =
        repository.sortByPrioridadBaja.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val PrioridadAlta: StateFlow<List<Tarea>> =
        repository.sortByPrioridadAlta.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    init {
        getAllTareas()
        readSortEstados()
    }

    private fun getAllTareas() {
        _allTareas.value = Estados.Cargando
        try {
            viewModelScope.launch {
                repository.getAllTareas.collect {
                    _allTareas.value = Estados.Exito(it)
                }
            }
        } catch (e: Exception) {
            _allTareas.value = Estados.Error(e)
        }
    }

    fun getSelecionarTarea(tareaId: Int) {
        viewModelScope.launch {
            repository.getSelecionarTarea(tareaId = tareaId).collect { tarea ->
                _selecionTareas.value = tarea
            }
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val tarea = Tarea(
                nombre = nombre.value,
                descripcion = descripcion.value,
                prioridad = prioridad.value
            )
            repository.addTarea(tarea = tarea)
        }
        buscarAppEstados.value = BuscarAppEstados.CLOSED
    }

    private fun updateTarea() {
        viewModelScope.launch(Dispatchers.IO) {
            val tarea = Tarea(
                id = id.value,
                nombre = nombre.value,
                descripcion = descripcion.value,
                prioridad = prioridad.value
            )
            repository.updateTarea(tarea = tarea)
        }
    }

    fun updateTareaFields(selecionarTarea: Tarea?) {
        if (selecionarTarea != null) {
            id.value = selecionarTarea.id
            nombre.value = selecionarTarea.nombre
            descripcion.value = selecionarTarea.descripcion
            prioridad.value = selecionarTarea.prioridad
        } else {
            id.value = 0
            nombre.value = ""
            descripcion.value = ""
            prioridad.value = Prioridad.MEDIO
        }
    }

    fun updateNombre(nuevoNombre: String) {
        if (nuevoNombre.length < MAX_TITLE_LENGTH) nombre.value = nuevoNombre
    }

    fun buscarBaseDatos(buscarQuery: String) {
        _searchTareas.value = Estados.Cargando
        try {
            viewModelScope.launch {
                repository.BuscarDatabase(busqueda = "%$buscarQuery%")
                    .collect { buscarTarea ->
                        _searchTareas.value = Estados.Exito(buscarTarea)
                    }
            }
        } catch (e: Exception) {
            _searchTareas.value = Estados.Error(e)
        }
        buscarAppEstados.value = BuscarAppEstados.TRIGGERED
    }

    private fun readSortEstados() {
        _sortEstados.value = Estados.Cargando
        try {
            viewModelScope.launch {
                dataRepository.readSortEstado
                    .map { Prioridad.valueOf(it) }
                    .collect {
                        _sortEstados.value = Estados.Exito(it)
                    }
            }
        } catch (e: Exception) {
            _sortEstados.value = Estados.Error(e)
        }
    }

    fun persistSortEstado(prioridad: Prioridad) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.persistSortEstado(prioridad = prioridad)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val tarea = Tarea(
                id = id.value,
                nombre = nombre.value,
                descripcion = descripcion.value,
                prioridad = prioridad.value
            )
            repository.deleteTarea(tarea = tarea)
        }
    }

    private fun deleteAllTarea() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTarea()
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {
                updateTarea()
            }
            Action.DELETE -> {
                deleteTask()
            }
            Action.DELETE_ALL -> {
                deleteAllTarea()
            }
            Action.UNDO -> {
                addTask()
            }
            else -> {}
        }
    }

    fun validateFields(): Boolean =
        nombre.value.isNotEmpty() && descripcion.value.isNotEmpty()

    fun update(id: String, tarea: TareaDto){
        viewModelScope.launch {
            api.updateAgenda(id, tarea)
        }
    }

    fun searchById(id: String?){
        viewModelScope.launch {
            api.getAgenda(id)
        }
    }

    fun save(tarea: TareaDto){
        viewModelScope.launch {
            api.insetAgenda(tarea)
        }
    }

    fun deleteTarea(tarea: TareaDto){
        viewModelScope.launch {
            api.updateAgenda(tarea.tareaId.toString(),tarea)
        }
    }
}