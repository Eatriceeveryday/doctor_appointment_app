package com.mufr.klinikku.domain.entities.doctor

import com.google.gson.annotations.SerializedName

data class DoctorAppointmentSchedule(
    @field:SerializedName("scheduleId")
    val id: String,

    @field:SerializedName("hour")
    val hour: String
)
