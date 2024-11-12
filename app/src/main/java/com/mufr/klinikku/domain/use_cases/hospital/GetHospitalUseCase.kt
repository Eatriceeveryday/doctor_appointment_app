package com.mufr.klinikku.domain.use_cases.hospital

import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.hospital.Hospital
import com.mufr.klinikku.domain.repository.HospitalRepository

class GetHospitalUseCase(
    private val repository: HospitalRepository
) {
    suspend operator fun invoke(): Resource<List<Hospital>>{
        return repository.getHospital()
    }
}