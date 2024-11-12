package com.mufr.klinikku.domain.entities.hospital

import com.google.gson.annotations.SerializedName

data class GetHospitalResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: HospitalData

)

data class HospitalData(
    @field:SerializedName("hospitals")
    val hospitals: List<Hospital>
)
