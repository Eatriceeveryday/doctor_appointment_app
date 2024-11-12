package com.mufr.klinikku.domain.use_cases.patient

import com.mufr.klinikku.domain.entities.general.GeneralResult
import com.mufr.klinikku.domain.entities.patient.AddPatientRequest
import com.mufr.klinikku.domain.repository.PatientRepository
import com.mufr.klinikku.shared.isValidDate

class EditPatientUseCase(
    private val patientRepository: PatientRepository
) {
    suspend operator fun invoke(
        request: AddPatientRequest,
        patientId: String
    ): GeneralResult{

        if (patientId.isBlank()){
            return GeneralResult(
                error = "Something wrong"
            )
        }

        if (request.name.isBlank()){
            return GeneralResult(
                error = "Nama pasien kosong"
            )
        }

        if (request.gender.isBlank()){
            return GeneralResult(
                error = "Jenis kelamin pasien kosong"
            )
        }

        if (request.dateOfBirth.isBlank() || !request.dateOfBirth.isValidDate()){
            return GeneralResult(
                error = "Tanggal lahir pasien kosong"
            )
        }
        return GeneralResult(
            result = patientRepository.editPatient(request, patientId)
        )
    }

}