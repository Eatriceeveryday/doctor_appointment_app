package com.mufr.klinikku.domain.entities.appointment

import com.google.gson.annotations.SerializedName

data class CreateAppointmentResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: Appointment
)


