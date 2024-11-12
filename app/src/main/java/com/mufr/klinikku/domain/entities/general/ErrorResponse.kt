package com.mufr.klinikku.domain.entities.general

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("message")
    val message: String,
)