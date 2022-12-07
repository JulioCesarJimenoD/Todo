package ucne.edu.todo.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tareas")
data class Tarea(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val prioridad: Prioridad
)
