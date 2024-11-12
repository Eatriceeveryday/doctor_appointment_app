package com.mufr.klinikku.data.remote.api_service

import com.mufr.klinikku.domain.entities.appointment.ChangeAppointmentRequest
import com.mufr.klinikku.domain.entities.appointment.CreateAppointmentRequest
import com.mufr.klinikku.domain.entities.appointment.CreateAppointmentResponse
import com.mufr.klinikku.domain.entities.appointment.GetAppointmentResponse
import com.mufr.klinikku.domain.entities.general.GeneralResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AppointmentApiService {
    @Headers("Content-Type: application/json")
    @POST("appointment")
    suspend fun createAppointment(
        @Header("Authorization") token: String,
        @Body body: CreateAppointmentRequest
    ): CreateAppointmentResponse

    @Headers("Content-Type: application/json")
    @GET("appointment")
    suspend fun getAppointment(
        @Header("Authorization") token: String,
    ): GetAppointmentResponse

    @Headers("Content-Type: application/json")
    @PUT("appointment")
    suspend fun changeAppointment(
        @Header("Authorization") token: String,
        @Query("appointment_id") appointmentId: String,
        @Body body: ChangeAppointmentRequest
    ): GeneralResponse
}