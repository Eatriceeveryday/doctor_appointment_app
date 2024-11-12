package com.mufr.klinikku.domain.entities.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("contactNumber")
    val contactNumber: String


)