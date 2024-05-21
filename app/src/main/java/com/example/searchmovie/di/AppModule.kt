package com.example.searchmovie.di

import com.example.searchmovie.BuildConfig
import com.example.searchmovie.data.remote.ConfigApiService
import com.example.searchmovie.data.remote.MovieApiService
import com.example.searchmovie.data.repository.ConfigRepositoryImpl
import com.example.searchmovie.data.repository.MovieRepositoryImpl
import com.example.searchmovie.domain.repository.ConfigRepository
import com.example.searchmovie.domain.repository.MovieRepository
import com.example.searchmovie.util.ConfigManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val retrofitInstance = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideConfigApiService(): ConfigApiService =
        retrofitInstance.create(ConfigApiService::class.java)

    @Provides
    @Singleton
    fun provideMovieApiService(): MovieApiService =
        retrofitInstance.create(MovieApiService::class.java)

    @Provides
    @Singleton
    fun provideConfigRepository(apiService: ConfigApiService): ConfigRepository =
        ConfigRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideMovieRepository(apiService: MovieApiService): MovieRepository =
        MovieRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideConfigManager(): ConfigManager = ConfigManager(Gson())
}




