package com.mufr.klinikku.data.remote.repository

import android.util.Log
import com.google.gson.Gson
import com.mufr.klinikku.data.local.AuthService
import com.mufr.klinikku.data.remote.api_service.AppointmentApiService
import com.mufr.klinikku.domain.entities.appointment.Appointment
import com.mufr.klinikku.domain.entities.appointment.ChangeAppointmentRequest
import com.mufr.klinikku.domain.entities.appointment.CreateAppointmentRequest
import com.mufr.klinikku.domain.entities.general.ErrorResponse
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.repository.AppointmentRepository
import retrofit2.HttpException
import java.io.IOException

class AppointmentRepositoryImpl(
    private val userPreference: AuthService,
    private val appointmentApiService: AppointmentApiService
): AppointmentRepository {
    override suspend fun createAppointment(request: CreateAppointmentRequest): Resource<Unit> {
        return try {
            val token = "Bearer " + userPreference.getAuthToken()
            val response = appointmentApiService.createAppointment(token, request)
            Resource.Success(Unit)
        }catch (e: IOException){
            Log.d("Error Value : " , e.toString())
            Resource.Error(e.message)
        }catch (e: HttpException){
            val errorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), ErrorResponse::class.java)
            Log.d("Error Response : " , errorResponse.message)
            if (e.code() == 401){
                return Resource.AuthError(errorResponse.message)
            }
            Resource.Error(errorResponse.message)
        }
    }

    override suspend fun getAppointment(): Resource<List<Appointment>> {
        return try {
            val token = "Bearer " + userPreference.getAuthToken()
            val response = appointmentApiService.getAppointment(token)
            Resource.Success(response.data.appointments)
        }catch (e: IOException){
            Log.d("Error Value : " , e.toString())
            Resource.Error(e.message)
        }catch (e: HttpException){
            val errorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), ErrorResponse::class.java)
            Log.d("Error Response : " , errorResponse.message)
            if (e.code() == 401){
                return Resource.AuthError(errorResponse.message)
            }
            Resource.Error(errorResponse.message)
        }
    }

    override suspend fun changeAppointment(
        appointmentId: String,
        request: ChangeAppointmentRequest
    ): Resource<Unit> {
        return try {
            val token = "Bearer " + userPreference.getAuthToken()
            val response = appointmentApiService.changeAppointment(token,appointmentId, request)
            Resource.Success(Unit)
        }catch (e: IOException){
            Log.d("Error Value : " , e.toString())
            Resource.Error(e.message)
        }catch (e: HttpException){
            val errorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), ErrorResponse::class.java)
            Log.d("Error Response : " , errorResponse.message)
            if (e.code() == 401){
                return Resource.AuthError(errorResponse.message)
            }
            Resource.Error(errorResponse.message)
        }
    }
}