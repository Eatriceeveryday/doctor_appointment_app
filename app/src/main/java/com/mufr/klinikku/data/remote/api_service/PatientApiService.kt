package com.mufr.klinikku.data.remote.api_service

import com.mufr.klinikku.domain.entities.general.GeneralResponse
import com.mufr.klinikku.domain.entities.patient.AddPatientRequest
import com.mufr.klinikku.domain.entities.patient.GetAllPatientResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PatientApiService {
    @Headers("Content-Type: application/json")
    @GET("patient")
    suspend fun getAllPatient(
        @Header("Authorization") token: String
    ): GetAllPatientResponse

    @Headers("Content-Type: application/json")
    @POST("patient")
    suspend fun addPatient(
        @Header("Authorization") token: String,
        @Body body: AddPatientRequest
    ): GeneralResponse

    @Headers("Content-Type: application/json")
    @PUT("patient")
    suspend fun editPatient(
        @Header("Authorization") token: String,
        @Body body: AddPatientRequest,
        @Query ("patient_id") patientId: String
    ): GeneralResponse
}