package com.mufr.klinikku.domain.repository

import com.mufr.klinikku.domain.entities.auth.LoginRequest
import com.mufr.klinikku.domain.entities.auth.RegisterRequest
import com.mufr.klinikku.domain.entities.general.Resource

interface AuthRepository {
    suspend fun login(request: LoginRequest): Resource<Unit>
    suspend fun register(request: RegisterRequest): Resource<Unit>
}