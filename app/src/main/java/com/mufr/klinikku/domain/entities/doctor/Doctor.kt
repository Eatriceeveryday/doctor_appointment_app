package com.mufr.klinikku.domain.entities.doctor

import com.google.gson.annotations.SerializedName

data class Doctor(
    @field:SerializedName("doctorId")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("hour")
    val hour: String,

    @field:SerializedName("specialization")
    val specialization: String
)
