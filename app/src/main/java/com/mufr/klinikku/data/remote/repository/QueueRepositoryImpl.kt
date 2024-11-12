package com.mufr.klinikku.data.remote.repository

import android.util.Log
import com.google.gson.Gson
import com.mufr.klinikku.data.local.AuthService
import com.mufr.klinikku.data.remote.api_service.QueueApiService
import com.mufr.klinikku.domain.entities.general.ErrorResponse
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.queue.Queue
import com.mufr.klinikku.domain.repository.QueueRepository
import retrofit2.HttpException
import java.io.IOException

class QueueRepositoryImpl(
    private val authService: AuthService,
    private val queueApiService: QueueApiService
): QueueRepository {
    override suspend fun addPatientToQueue(onDutyId: String, patientId: String): Resource<Unit> {
        return try {
            val token = "Bearer " + authService.getAuthToken()
            val response = queueApiService.addPatientToQueue(token, onDutyId, patientId)
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

    override suspend fun getQueue(): Resource<List<Queue>> {
        return try {
            val token = "Bearer " + authService.getAuthToken()
            val response = queueApiService.getQueue(token)
            Resource.Success(response.data.queue)
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