package com.mufr.klinikku.domain.entities.doctor

import com.google.gson.annotations.SerializedName

data class DoctorOnDuty(
    @field:SerializedName("OnDutyId")
    val id: String,

    @field:SerializedName("startHour")
    val startHour: String,

    @field:SerializedName("endHour")
    val endHour: String,

    @field:SerializedName("doctorId")
    val doctorId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("specialization")
    val specialization: String
)
