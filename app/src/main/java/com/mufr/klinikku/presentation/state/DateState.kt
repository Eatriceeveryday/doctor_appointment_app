package com.mufr.klinikku.presentation.state

import java.time.LocalDate

data class DateState(
    val date: LocalDate,
    val error: String? = null
)
