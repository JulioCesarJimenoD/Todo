package ucne.edu.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ucne.edu.todo.data.dao.TareaDao
import ucne.edu.todo.data.models.Tarea

@Database(
    entities = [Tarea::class],
    version = 1,
    exportSchema = false)

abstract class TareaDatabase: RoomDatabase() {
    abstract fun tareaDao(): TareaDao
}