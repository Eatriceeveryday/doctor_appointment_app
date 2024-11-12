package com.mufr.klinikku.domain.entities.patient

import com.google.gson.annotations.SerializedName

data class AddPatientRequest(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("date_of_birth")
    val dateOfBirth: String,

    @field:SerializedName("gender")
    val gender: String
)