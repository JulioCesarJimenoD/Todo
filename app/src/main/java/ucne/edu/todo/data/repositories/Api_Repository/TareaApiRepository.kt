package ucne.edu.todo.data.repositories.Api_Repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ucne.edu.todo.data.remote.TareaApi
import ucne.edu.todo.data.remote.dto.TareaDto
import ucne.edu.todo.utils.Resource
import java.io.IOException
import javax.inject.Inject

class TareaApiRepository @Inject constructor(
    private  val api: TareaApi
) {
    fun getTarea(): Flow<Resource<List<TareaDto>>> = flow {

        try {
            emit(Resource.Loading())
            val tarea = api.getAll()
            emit(Resource.Success(tarea))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "Verificar tu conexion a internet"))
        }
    }
    suspend fun postAgenda(tareaDto: TareaDto): TareaDto {
        return api.insert(tareaDto)
    }
}
