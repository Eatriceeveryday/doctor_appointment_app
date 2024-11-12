package com.mufr.klinikku.data.remote.repository

import android.util.Log
import com.google.gson.Gson
import com.mufr.klinikku.data.local.AuthService
import com.mufr.klinikku.data.remote.api_service.AuthApiService
import com.mufr.klinikku.domain.entities.auth.LoginRequest
import com.mufr.klinikku.domain.entities.auth.RegisterRequest
import com.mufr.klinikku.domain.entities.general.ErrorResponse
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val authService: AuthService
): AuthRepository {
    override suspend fun login(request: LoginRequest): Resource<Unit> {
        return try {
            val response = authApiService.login(request)
            authService.saveAuthToken(response.data.token)
            Resource.Success(Unit)
        }catch (e: IOException){
            Log.d("Error Value : " , e.toString())
            Resource.Error("${e.message}")
        }catch (e: HttpException){
            Log.d("Error Body Response Value : ", e.response()?.errorBody().toString())
            val errorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), ErrorResponse::class.java)
            Log.d("Error Response Value : ", errorResponse.toString())
            Resource.Error(errorResponse.message)

        }
    }

    override suspend fun register(request: RegisterRequest): Resource<Unit> {
        return try {
            authApiService.register(request)
            Resource.Success(Unit)
        }catch (e: IOException){
            Log.d("Error Value : " , e.toString())
            Resource.Error("${e.message}")
        }catch (e: HttpException){
            Log.d("Error Body Response Value : ", e.response()?.errorBody().toString())
            val errorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), ErrorResponse::class.java)
            Log.d("Error Response Value : ", errorResponse.toString())
            Resource.Error(errorResponse.message)

        }

    }
}