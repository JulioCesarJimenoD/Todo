package ucne.edu.todo.data.remote

import androidx.room.Dao
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ucne.edu.todo.data.models.Tarea
import ucne.edu.todo.data.remote.dto.TareaDto

@Dao
interface TareaApi {

    @GET("/Tarea/GetTarea")
    suspend fun getAll(): List<TareaDto>

    @GET("/Tarea/GetTarea{id}")
    suspend fun getByid(@Path("id") id: String): TareaDto

    @PUT("/Tarea/PutTarea{id}")
    suspend fun update(@Path("id") id: String, @Body tarea: TareaDto): TareaDto

    @POST("/Tarea/PostTarea")
    suspend fun insert(@Body tarea: TareaDto): TareaDto

}