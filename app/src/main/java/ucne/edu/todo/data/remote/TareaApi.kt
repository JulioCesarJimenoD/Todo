package ucne.edu.todo.data.remote

import androidx.room.Dao
import retrofit2.http.*
import ucne.edu.todo.data.models.Tarea
import ucne.edu.todo.data.remote.dto.TareaDto

@Dao
interface TareaApi {

    @GET("/Agenda/GetAgenda")
    suspend fun getAll(): List<TareaDto>

    @GET("/Agenda/GetAgenda(id}")
    suspend fun getByid(@Path("id") id: String): TareaDto

    @PUT("/Agenda/PutAgenda{id}")
    suspend fun update(@Path("id") id: String, @Body tarea: TareaDto): TareaDto

    @POST("/Agenda/PostAgenda")
    suspend fun insert(@Body tarea: TareaDto): TareaDto

    @DELETE("/Agenda/Delete{id}")
    suspend fun delete(@Path("id") id :String):TareaDto
}