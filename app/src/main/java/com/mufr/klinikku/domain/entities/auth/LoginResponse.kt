package com.mufr.klinikku.domain.entities.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: TokenData
)

data class TokenData(
    @field:SerializedName("token")
    val token: String
)
