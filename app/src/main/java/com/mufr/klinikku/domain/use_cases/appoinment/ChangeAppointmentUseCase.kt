package com.mufr.klinikku.domain.use_cases.appoinment

import com.mufr.klinikku.domain.entities.appointment.ChangeAppointmentRequest
import com.mufr.klinikku.domain.entities.general.GeneralResult
import com.mufr.klinikku.domain.repository.AppointmentRepository
import com.mufr.klinikku.shared.isValidDate

class ChangeAppointmentUseCase(
    private val appointmentRepository: AppointmentRepository
) {
    suspend operator fun invoke(
        appointmentId: String,
        request: ChangeAppointmentRequest
    ):GeneralResult{
        if (appointmentId.isBlank()){
            GeneralResult(
                error = "Something is wrong"
            )
        }

        if (request.patientId.isBlank()){
            GeneralResult(
                error = "missing patient id"
            )
        }

        if (request.scheduleId.isBlank()){
            GeneralResult(
                error = "missing schedule id"
            )
        }

        if (request.appointmentDate.isBlank() || !request.appointmentDate.isValidDate()){
            GeneralResult(
                error = "appointment date is missing"
            )
        }

        return GeneralResult(
            result = appointmentRepository.changeAppointment(
                appointmentId,
                request
            )
        )
    }
}