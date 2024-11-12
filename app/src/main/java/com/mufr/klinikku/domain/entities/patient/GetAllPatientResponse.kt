package com.mufr.klinikku.domain.entities.patient

import com.google.gson.annotations.SerializedName

data class GetAllPatientResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: AllPatientData
)

data class AllPatientData(
    @field:SerializedName("patients")
    val patients: List<Patient>
)
