package com.mufr.klinikku.domain.use_cases.queue

import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.queue.Queue
import com.mufr.klinikku.domain.repository.QueueRepository

class GetQueueUseCase(
    private val queueRepository: QueueRepository
) {
    suspend operator fun invoke():Resource<List<Queue>>{
        return queueRepository.getQueue()
    }
}