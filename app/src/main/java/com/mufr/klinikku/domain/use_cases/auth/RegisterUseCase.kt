package com.mufr.klinikku.domain.use_cases.auth

import com.mufr.klinikku.domain.entities.auth.AuthResult
import com.mufr.klinikku.domain.entities.auth.RegisterRequest
import com.mufr.klinikku.domain.repository.AuthRepository
import com.mufr.klinikku.shared.isEmail

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        request: RegisterRequest
    ): AuthResult{
        if (request.email.isBlank() || !request.email.isEmail()){
            return AuthResult(
                error = "Email Kosong"
            )
        }

        if (request.password.isBlank()){
            return AuthResult(
                error = "Password Kosong"
            )
        }

        if (request.username.isBlank()){
            return AuthResult(
                error = "Nama Kosong"
            )
        }

        if (request.dateOfBirth.isBlank()){
            return AuthResult(
                error = "Tanggal Lahir kosong"
            )
        }

        if (request.gender.isBlank()){
            return AuthResult(
                error = "Jenis Kelamin Kosong"
            )
        }

        if (request.contactNumber.isBlank()){
            return AuthResult(
                error = "Contact Number is missing"
            )
        }

        return AuthResult(
            result = repository.register(request)
        )

    }
}