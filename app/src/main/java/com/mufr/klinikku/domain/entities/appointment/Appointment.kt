package com.mufr.klinikku.domain.entities.appointment

import com.google.gson.annotations.SerializedName

data class Appointment(
    @field:SerializedName("appointmentId")
    val id: String,

    @field:SerializedName("appointmentTime")
    val time: String? = null,

    @field:SerializedName("patientId")
    val patientId: String ?= null,

    @field:SerializedName("patientName")
    val patientName: String ?= null,

    @field:SerializedName("doctorId")
    val doctorId: String ?= null,

    @field:SerializedName("doctorName")
    val doctorName: String ?= null
)


