package com.mufr.klinikku.domain.repository

import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.patient.AddPatientRequest
import com.mufr.klinikku.domain.entities.patient.Patient

interface PatientRepository {
    suspend fun getAllPatient(): Resource<List<Patient>>
    suspend fun addPatient(request: AddPatientRequest): Resource<Unit>
    suspend fun editPatient(request: AddPatientRequest, patientId: String): Resource<Unit>
}