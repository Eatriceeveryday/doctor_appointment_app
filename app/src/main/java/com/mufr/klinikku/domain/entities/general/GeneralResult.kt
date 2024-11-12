package com.mufr.klinikku.domain.entities.general

data class GeneralResult(
    val result: Resource<Unit>? = null,
    val error: String? = null
)
