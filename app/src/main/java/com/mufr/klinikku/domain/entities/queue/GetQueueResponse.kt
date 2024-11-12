package com.mufr.klinikku.domain.entities.queue

import com.google.gson.annotations.SerializedName

data class GetQueueResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: QueueData
)

data class QueueData(
    @field:SerializedName("queues")
    val queue: List<Queue>
)
