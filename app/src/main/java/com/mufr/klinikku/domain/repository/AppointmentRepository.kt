package com.mufr.klinikku.domain.repository

import com.mufr.klinikku.domain.entities.appointment.Appointment
import com.mufr.klinikku.domain.entities.appointment.ChangeAppointmentRequest
import com.mufr.klinikku.domain.entities.appointment.CreateAppointmentRequest
import com.mufr.klinikku.domain.entities.general.Resource

interface AppointmentRepository {
    suspend fun createAppointment(request: CreateAppointmentRequest): Resource<Unit>
    suspend fun getAppointment(): Resource<List<Appointment>>
    suspend fun changeAppointment(appointmentId: String, request: ChangeAppointmentRequest): Resource<Unit>
}