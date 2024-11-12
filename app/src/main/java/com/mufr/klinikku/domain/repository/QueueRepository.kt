package com.mufr.klinikku.domain.repository

import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.queue.Queue

interface QueueRepository {
    suspend fun addPatientToQueue(onDutyId: String, patientId: String): Resource<Unit>
    suspend fun getQueue(): Resource<List<Queue>>
}