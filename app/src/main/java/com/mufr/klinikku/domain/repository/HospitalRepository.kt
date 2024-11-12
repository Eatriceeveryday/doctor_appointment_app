package com.mufr.klinikku.domain.repository

import com.mufr.klinikku.domain.entities.doctor.Doctor
import com.mufr.klinikku.domain.entities.doctor.DoctorAppointmentSchedule
import com.mufr.klinikku.domain.entities.doctor.DoctorOnDuty
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.hospital.Hospital

interface HospitalRepository {
    suspend fun getHospital(): Resource<List<Hospital>>
    suspend fun getHospitalDoctorAppointment(hospitalId: String): Resource<List<Doctor>>
    suspend fun getDoctorAppointmentAvailableSchedule(doctorId: String, date: String): Resource<List<DoctorAppointmentSchedule>>
    suspend fun getHospitalDoctorOnDuty(hospitalId: String): Resource<List<DoctorOnDuty>>
}