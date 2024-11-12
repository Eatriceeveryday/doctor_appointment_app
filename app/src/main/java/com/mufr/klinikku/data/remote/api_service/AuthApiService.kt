package com.mufr.klinikku.data.remote.api_service

import com.mufr.klinikku.domain.entities.auth.LoginRequest
import com.mufr.klinikku.domain.entities.auth.LoginResponse
import com.mufr.klinikku.domain.entities.auth.RegisterRequest
import com.mufr.klinikku.domain.entities.general.GeneralResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {
    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun register(
        @Body body: RegisterRequest
    ): GeneralResponse
}