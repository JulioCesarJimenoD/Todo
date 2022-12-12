package ucne.edu.todo.data.repositories.Api_Repository

import ucne.edu.todo.data.TareaDatabase
import ucne.edu.todo.data.remote.dto.TareaDto
import javax.inject.Inject

class TareaApiRepository @Inject constructor(
    private val api: TareaDatabase
){

    suspend fun getAgendas(): List<TareaDto>{
        try{
            val api = api.getAll();
            return api
        }catch (e: Exception){
            println(e)
            return emptyList()
        }
    }

    suspend fun getAgenda(id:String?): TareaDto?{
        try {
            return this.api.getByid(id ?: "")
        }catch (e: Exception){
            println(e)
            return null
        }
    }
    suspend fun insetAgenda(tarea: TareaDto): TareaDto{
        try {
            return this.api.insert(tarea)
        }catch (e: Exception){
            println(e)
            return tarea
        }
    }
    suspend fun deleteAgenda(id: String): Boolean{
        try {
            val api = api.delete(id)
            return true
        }catch (e: Exception){
            println(e)
            return false
        }
    }

    suspend fun updateAgenda(id: String, tarea: TareaDto): TareaDto{
        try {
            return this.api.update(id, tarea)
        }catch (e: Exception){
            println(e)
            return tarea
        }
    }

}
