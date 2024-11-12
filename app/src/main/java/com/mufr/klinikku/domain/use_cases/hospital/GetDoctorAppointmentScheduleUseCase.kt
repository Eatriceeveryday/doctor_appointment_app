package com.mufr.klinikku.domain.use_cases.hospital

import com.mufr.klinikku.domain.entities.doctor.DoctorAppointmentSchedule
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.repository.HospitalRepository
import com.mufr.klinikku.shared.isValidDate

class GetDoctorAppointmentScheduleUseCase(
    private val hospitalRepository: HospitalRepository
) {
    suspend operator fun invoke(
        date: String,
        doctorId: String
    ): Resource<List<DoctorAppointmentSchedule>>{
        if (doctorId.isEmpty()){
            return Resource.Error("Invalid doctor Id")
        }

        if (!date.isValidDate()){
            return Resource.Error("Invalid Date")
        }

        return hospitalRepository.getDoctorAppointmentAvailableSchedule(doctorId,date)
    }

}