package com.mufr.klinikku.data.remote.api_service

import com.mufr.klinikku.domain.entities.doctor.GetDoctorAppointmentAvailableScheduleResponse
import com.mufr.klinikku.domain.entities.hospital.GetHospitalDoctorAppointment
import com.mufr.klinikku.domain.entities.hospital.GetHospitalDoctorOnDutyResponse
import com.mufr.klinikku.domain.entities.hospital.GetHospitalResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface HospitalApiService {
    @Headers("Content-Type: application/json")
    @GET("hospital")
    suspend fun getHospital(
        @Header("Authorization") token: String
    ):GetHospitalResponse

    @Headers("Content-Type: application/json")
    @GET("hospital/appointment")
    suspend fun getHospitalDoctorAppointmentSchedule(
        @Header("Authorization") token: String,
        @Query("hospital_id") hospitalId: String
    ):GetHospitalDoctorAppointment

    @Headers("Content-Type: application/json")
    @GET("hospital/appointment/{doctor_id}")
    suspend fun getDoctorAppointmentAvailability(
        @Header("Authorization") token: String,
        @Path("doctor_id") doctorId: String,
        @Query("date") date: String
    ): GetDoctorAppointmentAvailableScheduleResponse

    @Headers("Content-Type: application/json")
    @GET("hospital/on-duty")
    suspend fun getHospitalDoctorOnDuty(
        @Header("Authorization") token: String,
        @Query("hospital_id") hospitalId: String
    ):GetHospitalDoctorOnDutyResponse

}