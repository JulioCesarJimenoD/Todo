package ucne.edu.todo.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ucne.edu.todo.data.models.Tarea
import javax.inject.Singleton

@Dao
@Singleton
interface TareaDao {
    @Query("SELECT * FROM Tareas ORDER BY id ASC")
    fun getAllTareas(): Flow<List<Tarea>>

    @Query("SELECT * FROM Tareas WHERE id=:tareaId")
    fun getSelecionarTarea(tareaId: Int): Flow<Tarea>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTarea(tarea: Tarea)

    @Update
    suspend fun updateTarea(tarea: Tarea)

    @Delete
    suspend fun deleteTarea(tarea: Tarea)

    @Query("DELETE FROM Tareas")
    suspend fun deleteAllTareas()

    @Query("SELECT * FROM Tareas WHERE nombre LIKE :busqueda OR descripcion LIKE :busqueda")
    fun Buscar(busqueda: String): Flow<List<Tarea>>

    //ordenar
    @Query(
        """
       SELECT * FROM Tareas ORDER BY
    CASE
        WHEN prioridad LIKE 'L%' THEN 1
        WHEN prioridad LIKE 'M%' THEN 2
        WHEN prioridad LIKE 'H%' THEN 3
    END
    """
    )
    fun sortByLowPriority(): Flow<List<Tarea>>

    @Query(
        """
        SELECT * FROM Tareas ORDER BY
    CASE
        WHEN prioridad LIKE 'H%' THEN 1
        WHEN prioridad LIKE 'M%' THEN 2
        WHEN prioridad LIKE 'L%' THEN 3
    END
    """
    )
    fun sortByHighPriority(): Flow<List<Tarea>>
}