package com.mufr.klinikku.domain.use_cases.hospital

import com.mufr.klinikku.domain.entities.doctor.DoctorOnDuty
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.repository.HospitalRepository

class GetHospitalDoctorOnDutyUseCase(
    private val hospitalRepository: HospitalRepository
) {
    suspend operator fun invoke(
        hospitalId: String
    ):Resource<List<DoctorOnDuty>>{
        if (hospitalId.isEmpty()){
            return Resource.Error("Invalid ID")
        }


        return hospitalRepository.getHospitalDoctorOnDuty(hospitalId)
    }
}