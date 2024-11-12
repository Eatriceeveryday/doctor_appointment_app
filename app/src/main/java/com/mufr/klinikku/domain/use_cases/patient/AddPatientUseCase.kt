package com.mufr.klinikku.domain.use_cases.patient

import com.mufr.klinikku.domain.entities.patient.AddPatientRequest
import com.mufr.klinikku.domain.entities.patient.AddPatientResult
import com.mufr.klinikku.domain.repository.PatientRepository
import com.mufr.klinikku.shared.isValidDate

class AddPatientUseCase (
    private val patientRepository: PatientRepository
) {
    suspend operator fun invoke(
        request: AddPatientRequest
    ): AddPatientResult{
        if (request.name.isBlank()){
            return AddPatientResult(
                error = "Nama pasien kosong"
            )
        }

        if (request.gender.isBlank()){
            return AddPatientResult(
                error = "Jenis kelamin pasien kosong"
            )
        }

        if (request.dateOfBirth.isBlank() || !request.dateOfBirth.isValidDate()){
            return AddPatientResult(
                error = "Tanggal lahir pasien kosong"
            )
        }

        return AddPatientResult(
            result = patientRepository.addPatient(request)
        )
    }
}