package com.mufr.klinikku.domain.entities.hospital

import com.google.gson.annotations.SerializedName

data class Hospital(
    @field:SerializedName("hospitalId")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("contactNumber")
    val contactNumber: String
)
