package com.mufr.klinikku.domain.use_cases.patient

import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.patient.Patient
import com.mufr.klinikku.domain.repository.PatientRepository

class GetAllPatientUseCase(
    private val patientRepository: PatientRepository
) {
    suspend operator fun invoke(): Resource<List<Patient>>{
        return patientRepository.getAllPatient()
    }
}