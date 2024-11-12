package com.mufr.klinikku.domain.entities.doctor

import com.google.gson.annotations.SerializedName

data class GetDoctorAppointmentAvailableScheduleResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: List<DoctorAppointmentSchedule>
)

