package com.mufr.klinikku.domain.entities.appointment

import com.google.gson.annotations.SerializedName

data class CreateAppointmentRequest(
    @field:SerializedName("patientId")
    val patientId: String,

    @field:SerializedName("scheduleId")
    val scheduleId: String,

    @field:SerializedName("appointmentDate")
    val appointmentDate: String
)
