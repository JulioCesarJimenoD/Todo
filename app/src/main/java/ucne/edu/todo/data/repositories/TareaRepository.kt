package ucne.edu.todo.data.repositories

import kotlinx.coroutines.flow.Flow
import ucne.edu.todo.data.dao.TareaDao
import ucne.edu.todo.data.models.Tarea
import java.sql.RowId
import javax.inject.Inject

class TareaRepository @Inject constructor(private val tareaDao: TareaDao)
{
    val getAllTareas: Flow<List<Tarea>> = tareaDao.getAllTareas()
    val sortByPrioridadBaja: Flow<List<Tarea>> = tareaDao.sortByLowPriority()
    val sortByPrioridadAlta: Flow<List<Tarea>> = tareaDao.sortByHighPriority()

    fun getSelecionarTarea(tareaId: Int): Flow<Tarea> =
        tareaDao.getSelecionarTarea(tareaId = tareaId)

    suspend fun addTarea(tarea: Tarea){
        tareaDao.addTarea(tarea = tarea)
    }

    suspend fun updateTarea(tarea: Tarea){
        tareaDao.updateTarea(tarea = tarea)
    }

    suspend fun deleteTarea(tarea: Tarea) {
        tareaDao.deleteTarea(tarea = tarea)
    }

    suspend fun deleteAllTarea(){
        tareaDao.deleteAllTareas()
    }

    fun BuscarDatabase(busqueda : String): Flow<List<Tarea>> =
        tareaDao.Buscar(busqueda = busqueda)

}