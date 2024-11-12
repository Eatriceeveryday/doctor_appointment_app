package com.mufr.klinikku.shared

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun String.isEmail() : Boolean {
    val regex = Regex(
        "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
    )
    return regex.matches(this)
}

fun String.isValidDate(): Boolean {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        LocalDate.parse(this,formatter)
        true
    } catch (e: DateTimeParseException) {
        false
    }
}