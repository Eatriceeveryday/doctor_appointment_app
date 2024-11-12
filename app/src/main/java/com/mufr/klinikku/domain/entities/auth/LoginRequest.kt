package com.mufr.klinikku.domain.entities.auth

data class LoginRequest(
    val email: String,
    val password: String
)