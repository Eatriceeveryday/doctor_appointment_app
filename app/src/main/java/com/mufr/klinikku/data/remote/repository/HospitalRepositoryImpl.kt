package com.mufr.klinikku.data.remote.repository

import android.util.Log
import com.google.gson.Gson
import com.mufr.klinikku.data.local.AuthService
import com.mufr.klinikku.data.remote.api_service.HospitalApiService
import com.mufr.klinikku.domain.entities.doctor.Doctor
import com.mufr.klinikku.domain.entities.doctor.DoctorAppointmentSchedule
import com.mufr.klinikku.domain.entities.doctor.DoctorOnDuty
import com.mufr.klinikku.domain.entities.general.ErrorResponse
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.hospital.Hospital
import com.mufr.klinikku.domain.repository.HospitalRepository
import retrofit2.HttpException
import java.io.IOException

class HospitalRepositoryImpl(
    private val hospitalApiService: HospitalApiService,
    private val userPreference: AuthService
): HospitalRepository {
    override suspend fun getHospital(): Resource<List<Hospital>> {
        return try {
            val token = "Bearer " + userPreference.getAuthToken()
            val response = hospitalApiService.getHospital(token)
            Resource.Success(response.data.hospitals)
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

    override suspend fun getHospitalDoctorAppointment(hospitalId: String): Resource<List<Doctor>> {
        return try {
            val token = "Bearer " + userPreference.getAuthToken()
            val response = hospitalApiService.getHospitalDoctorAppointmentSchedule(token, hospitalId )
            Resource.Success(response.data.doctors)
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

    override suspend fun getDoctorAppointmentAvailableSchedule(
        doctorId: String,
        date: String
    ): Resource<List<DoctorAppointmentSchedule>> {
        return try {
            val token = "Bearer " + userPreference.getAuthToken()
            val response = hospitalApiService.getDoctorAppointmentAvailability(token,doctorId,date)
            Resource.Success(response.data)
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

    override suspend fun getHospitalDoctorOnDuty(hospitalId: String): Resource<List<DoctorOnDuty>> {
        return try {
            val token = "Bearer " + userPreference.getAuthToken()
            val response = hospitalApiService.getHospitalDoctorOnDuty(token, hospitalId )
            Resource.Success(response.data.doctors)
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