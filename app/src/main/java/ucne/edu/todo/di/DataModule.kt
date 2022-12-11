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
import ucne.edu.todo.utils.Constante.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object DataModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        TareaDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: TareaDatabase) = database.tareaDao()

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
    fun providesTareaApi(moshi: Moshi): TareaApi {
        return Retrofit.Builder()
            .baseUrl("http://www.todoapp.somee.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TareaApi::class.java)
    }



}