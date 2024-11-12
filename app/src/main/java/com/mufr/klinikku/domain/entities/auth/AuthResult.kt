package com.mufr.klinikku.domain.entities.auth

import com.mufr.klinikku.domain.entities.general.Resource

data class AuthResult(
    val result: Resource<Unit>? = null,
    val error: String? = null
)
