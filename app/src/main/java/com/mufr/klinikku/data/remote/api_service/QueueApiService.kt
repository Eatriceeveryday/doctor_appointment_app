package com.mufr.klinikku.data.remote.api_service

import com.mufr.klinikku.domain.entities.general.GeneralResponse
import com.mufr.klinikku.domain.entities.queue.GetQueueResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface QueueApiService {
    @Headers("Content-Type: application/json")
    @POST("queue")
    suspend fun addPatientToQueue(
        @Header("Authorization") token: String,
        @Query("on_duty_id") onDutyId: String,
        @Query("patient_id") patientId: String
    ): GeneralResponse

    @Headers("Content-Type: application/json")
    @GET("queue")
    suspend fun getQueue(
        @Header("Authorization") token: String
    ): GetQueueResponse
}