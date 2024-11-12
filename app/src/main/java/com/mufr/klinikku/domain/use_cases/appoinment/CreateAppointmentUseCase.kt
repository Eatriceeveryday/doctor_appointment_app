package com.mufr.klinikku.domain.use_cases.appoinment

import com.mufr.klinikku.domain.entities.appointment.CreateAppointmentRequest
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.repository.AppointmentRepository
import com.mufr.klinikku.shared.isValidDate

class CreateAppointmentUseCase (
    private val appointmentRepository: AppointmentRepository
) {
    suspend operator fun invoke(
        request: CreateAppointmentRequest
    ):Resource<Unit>{
        if (request.patientId.isEmpty()){
            return Resource.Error("Invalid patient id")
        }

        if (request.scheduleId.isEmpty()){
            return Resource.Error("Invalid schedule id")
        }

        if (request.appointmentDate.isEmpty() || !request.appointmentDate.isValidDate()){
            return Resource.Error("Invalid date")
        }
        return appointmentRepository.createAppointment(request)
    }
}