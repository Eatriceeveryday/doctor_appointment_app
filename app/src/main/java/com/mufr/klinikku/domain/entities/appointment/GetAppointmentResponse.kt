package com.mufr.klinikku.domain.entities.appointment

import com.google.gson.annotations.SerializedName

data class GetAppointmentResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: AppointmentData
)

data class AppointmentData(
    @field:SerializedName("appointments")
    val appointments: List<Appointment>
)
