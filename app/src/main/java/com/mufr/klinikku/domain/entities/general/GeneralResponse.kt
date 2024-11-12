package com.mufr.klinikku.domain.entities.general

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @field:SerializedName("message")
    val message: String,
    
)