package com.mufr.klinikku.domain.entities.patient

import com.mufr.klinikku.domain.entities.general.Resource

data class AddPatientResult(
    val result: Resource<Unit>? = null,
    val error: String? = null
)
