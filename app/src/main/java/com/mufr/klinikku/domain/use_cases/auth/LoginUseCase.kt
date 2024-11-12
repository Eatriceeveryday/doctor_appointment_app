package com.mufr.klinikku.domain.use_cases.auth

import com.mufr.klinikku.domain.entities.auth.AuthResult
import com.mufr.klinikku.domain.entities.auth.LoginRequest
import com.mufr.klinikku.domain.repository.AuthRepository
import com.mufr.klinikku.shared.isEmail

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        request: LoginRequest
    ): AuthResult{
        if (request.email.isBlank() || !request.email.isEmail()){
            return AuthResult(
                error = "Email Invalid"
            )
        }

        if (request.password.isBlank()){
            return AuthResult(
                error = "Password Kosong"
            )
        }

        return AuthResult(
            result = repository.login(request)
        )
    }
}