package com.example.searchmovie.data.repository

import com.example.searchmovie.data.remote.ConfigApiService
import com.example.searchmovie.data.remote.models.ConfigResponse.Companion.toConfig
import com.example.searchmovie.domain.repository.ConfigRepository
import com.example.searchmovie.util.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class ConfigRepositoryImpl @Inject constructor(private val apiService: ConfigApiService) :
    ConfigRepository {
    override suspend fun getConfig() = flow {
        try {
            val config = apiService.getConfiguration()
            emit(Resource.Success(config.toConfig()))
        } catch (exception: IOException) {
            emit(Resource.Error(exception.message.toString()))
        } catch (exception: HttpException) {
            emit(Resource.Error(exception.message.toString()))
        } catch (exception: SSLHandshakeException) {
            emit(Resource.Error(exception.message.toString()))
        }
    }
}
