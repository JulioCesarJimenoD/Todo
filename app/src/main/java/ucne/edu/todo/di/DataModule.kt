package ucne.edu.todo.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ucne.edu.todo.data.TareaDatabase
import ucne.edu.todo.data.remote.TareaApi
import ucne.edu.todo.data.repositories.Api_Repository.TareaApiRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object DataModule {

    @Singleton
    @Provides
    fun provideDatabase( @ApplicationContext context: Context): TareaDatabase{
        return Room.databaseBuilder(
            context,
            TareaDatabase::class.java,
            "ToDo_db"
        ).fallbackToDestructiveMigration().build()
    }


    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesAgendaApi(moshi: Moshi): TareaApi {
        return Retrofit.Builder()
            .baseUrl("todoapp.somee.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TareaApi::class.java)
    }

    @Singleton
    @Provides
    fun ProvideTareaRepository(tareaDatabase: TareaDatabase): TareaApiRepository {
        return TareaApiRepository(tareaDatabase)
    }

}