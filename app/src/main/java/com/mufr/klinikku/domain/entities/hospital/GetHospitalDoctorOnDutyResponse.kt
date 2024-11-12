package com.mufr.klinikku.domain.entities.hospital

import com.google.gson.annotations.SerializedName
import com.mufr.klinikku.domain.entities.doctor.DoctorOnDuty

data class GetHospitalDoctorOnDutyResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: DoctorOnDutyData
)

data class DoctorOnDutyData(
    @field:SerializedName("doctors")
    val doctors: List<DoctorOnDuty>
)


