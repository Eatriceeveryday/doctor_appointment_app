package com.mufr.klinikku.domain.use_cases.appoinment

import com.mufr.klinikku.domain.entities.appointment.Appointment
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.repository.AppointmentRepository

class GetAppointmentUseCase(
    private val appointmentRepository: AppointmentRepository
) {
    suspend operator fun invoke():Resource<List<Appointment>>{
        return appointmentRepository.getAppointment()
    }
}