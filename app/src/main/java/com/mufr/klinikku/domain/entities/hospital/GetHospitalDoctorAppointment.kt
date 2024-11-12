package com.mufr.klinikku.domain.entities.hospital

import com.google.gson.annotations.SerializedName
import com.mufr.klinikku.domain.entities.doctor.Doctor

data class GetHospitalDoctorAppointment(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: DoctorData
)

data class DoctorData(
    @field:SerializedName("doctors")
    val doctors: List<Doctor>
)
