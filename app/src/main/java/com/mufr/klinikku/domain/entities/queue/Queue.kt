package com.mufr.klinikku.domain.entities.queue

import com.google.gson.annotations.SerializedName

data class Queue(
    @field:SerializedName("queueId")
    val id: String,

    @field:SerializedName("predictionTime")
    val predictionTime: String,

    @field:SerializedName("queueNumber")
    val queueNumber: String,

    @field:SerializedName("doctorName")
    val doctorName: String,

    @field:SerializedName("patientName")
    val patientName: String
)