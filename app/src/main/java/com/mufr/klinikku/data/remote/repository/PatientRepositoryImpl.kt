package com.mufr.klinikku.data.remote.repository

import android.util.Log
import com.google.gson.Gson
import com.mufr.klinikku.data.local.AuthService
import com.mufr.klinikku.data.remote.api_service.PatientApiService
import com.mufr.klinikku.domain.entities.general.ErrorResponse
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.patient.AddPatientRequest
import com.mufr.klinikku.domain.entities.patient.Patient
import com.mufr.klinikku.domain.repository.PatientRepository
import retrofit2.HttpException
import java.io.IOException

class PatientRepositoryImpl(
    private val authService: AuthService,
    private val patientApiService: PatientApiService
): PatientRepository {
    override suspend fun getAllPatient(): Resource<List<Patient>> {
        return try {
            val token = "Bearer " + authService.getAuthToken()
            val response = patientApiService.getAllPatient(token)
            Resource.Success(response.data.patients)
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

    override suspend fun addPatient(request: AddPatientRequest): Resource<Unit> {
        return try {
            val token = "Bearer " + authService.getAuthToken()
            patientApiService.addPatient(token,request)
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

    override suspend fun editPatient(request: AddPatientRequest, patientId: String): Resource<Unit> {
        return try {
            val token = "Bearer " + authService.getAuthToken()
            patientApiService.editPatient(token,request, patientId)
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