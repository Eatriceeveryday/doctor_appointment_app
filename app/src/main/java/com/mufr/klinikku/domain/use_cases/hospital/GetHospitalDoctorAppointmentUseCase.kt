package com.mufr.klinikku.domain.use_cases.hospital

import com.mufr.klinikku.domain.entities.doctor.Doctor
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.repository.HospitalRepository

class GetHospitalDoctorAppointmentUseCase(
    private val hospitalRepository: HospitalRepository
) {
    suspend operator fun invoke(
        hospitalId: String
    ): Resource<List<Doctor>>{
        if (hospitalId.isEmpty()){
            return Resource.Error("Invalid ID")
        }
        return hospitalRepository.getHospitalDoctorAppointment(hospitalId)
    }
}