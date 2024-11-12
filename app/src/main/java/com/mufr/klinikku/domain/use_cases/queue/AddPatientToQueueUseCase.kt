package com.mufr.klinikku.domain.use_cases.queue

import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.repository.QueueRepository

class AddPatientToQueueUseCase(
    private val queueRepository: QueueRepository
) {
    suspend operator fun invoke(
        onDutyId: String,
        patientId: String
    ):Resource<Unit>{
        if (onDutyId.isEmpty()){
            return Resource.Error("Invalid on duty id")
        }

        if (patientId.isEmpty()){
            return Resource.Error("invalid patient id")
        }

        return queueRepository.addPatientToQueue(onDutyId, patientId)
    }
}