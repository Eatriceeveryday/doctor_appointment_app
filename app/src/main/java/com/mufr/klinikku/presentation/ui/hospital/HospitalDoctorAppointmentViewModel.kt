package com.mufr.klinikku.presentation.ui.hospital

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufr.klinikku.domain.entities.appointment.CreateAppointmentRequest
import com.mufr.klinikku.domain.entities.doctor.Doctor
import com.mufr.klinikku.domain.entities.doctor.DoctorAppointmentSchedule
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.patient.Patient
import com.mufr.klinikku.domain.use_cases.appoinment.CreateAppointmentUseCase
import com.mufr.klinikku.domain.use_cases.hospital.GetDoctorAppointmentScheduleUseCase
import com.mufr.klinikku.domain.use_cases.hospital.GetHospitalDoctorAppointmentUseCase
import com.mufr.klinikku.domain.use_cases.patient.GetAllPatientUseCase
import com.mufr.klinikku.presentation.state.TextFieldState
import com.mufr.klinikku.presentation.state.UiState
import com.mufr.klinikku.presentation.ui.UiEvent
import com.mufr.klinikku.presentation.ui.modal.convertMillisToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HospitalDoctorAppointmentViewModel @Inject constructor(
    private val getHospitalDoctorAppointmentUseCase: GetHospitalDoctorAppointmentUseCase,
    private val getAllPatientUseCase: GetAllPatientUseCase,
    private val getDoctorAppointmentScheduleUseCase: GetDoctorAppointmentScheduleUseCase,
    private val createAppointmentUseCase: CreateAppointmentUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val hospitalId: String = checkNotNull(savedStateHandle["hospitalId"])

    private var _hospitalState = mutableStateOf(UiState())
    val hospitalState: State<UiState> = _hospitalState

    private var _hospitalEvent = MutableSharedFlow<UiEvent>()
    val hospitalEvent = _hospitalEvent

    private var _doctorList = mutableStateOf(mutableStateListOf<Doctor>())
    val doctorList = _doctorList

    private var _appointmentDate = mutableStateOf(TextFieldState())
    val appointmentDate = _appointmentDate

    fun setAppointmentDate(value: String){
        _appointmentDate.value = appointmentDate.value.copy(text = value)
    }

    private var _doctorId = mutableStateOf(TextFieldState())
    val doctorId = _doctorId

    fun setDoctorId(value: String){
        _doctorId.value = doctorId.value.copy(text = value)
    }

    private var _appointmentList = mutableStateOf(mutableStateListOf<DoctorAppointmentSchedule>())
    val appointmentList = _appointmentList

    private var _patientList = mutableStateOf(mutableStateListOf<Patient>())
    val patientList = _patientList

    private var _patientId = mutableStateOf(TextFieldState())
    val patientId = _patientId

    fun setPatientId(id: String){
        _patientId.value = patientId.value.copy(text = id)
    }

    private var _scheduleId = mutableStateOf(TextFieldState())
    val scheduleId = _scheduleId

    fun setScheduleId(value: String){
        _scheduleId.value = scheduleId.value.copy(text = value)
    }

    init {
        getDoctor()
        getPatient()
        setAppointmentDate(convertMillisToDate(System.currentTimeMillis()))
    }

    private fun getDoctor(){
        viewModelScope.launch {
            _hospitalState.value = hospitalState.value.copy(isLoading = true)

            val result = getHospitalDoctorAppointmentUseCase(hospitalId)

            _hospitalState.value = hospitalState.value.copy(isLoading = false)

            when (result){
                is Resource.AuthError -> {
                    _hospitalEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }
                is Resource.Error -> {
                    _hospitalEvent.emit(
                        UiEvent.SnackBarEvent(
                            result.message ?: "Gagal Fetch Data"
                        )
                    )
                }
                is Resource.Success -> {
                    result.data?.let {
                        _doctorList.value.addAll(it)
                    }
                }
            }
        }

    }

    private fun getPatient(){
        viewModelScope.launch {
            _hospitalState.value = hospitalState.value.copy(isLoading = true)

            val result = getAllPatientUseCase()

            _hospitalState.value = hospitalState.value.copy(isLoading = false)

            when (result){
                is Resource.AuthError -> {
                    _hospitalEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }
                is Resource.Error -> {
                    _hospitalEvent.emit(
                        UiEvent.SnackBarEvent(
                            result.message ?: "Gagal Fetch Data"
                        )
                    )
                }
                is Resource.Success -> {
                    result.data?.let {
                        _patientList.value.addAll(it)
                    }
                }
            }
        }
    }

    fun getDoctorAvailability(){
        viewModelScope.launch {
            _hospitalState.value = hospitalState.value.copy(isLoading = true)

            Log.d("Check Value of AppointmentDate : " , appointmentDate.value.text)

            val result = getDoctorAppointmentScheduleUseCase(appointmentDate.value.text,doctorId.value.text)

            _hospitalState.value = hospitalState.value.copy(isLoading = false)

            when (result){
                is Resource.AuthError -> {
                    _hospitalEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }
                is Resource.Error -> {
                    _hospitalEvent.emit(
                        UiEvent.SnackBarEvent(
                            result.message ?: "Gagal Fetch Data"
                        )
                    )
                }
                is Resource.Success -> {
                    _appointmentList.value.clear()
                    result.data?.let {
                        _appointmentList.value.addAll(it)
                    }
                }
            }
        }
    }

    fun createAppointment(){
        viewModelScope.launch {
            _hospitalState.value = hospitalState.value.copy(isLoading = true)

            val result = createAppointmentUseCase(CreateAppointmentRequest(
                patientId = patientId.value.text,
                scheduleId = scheduleId.value.text,
                appointmentDate = appointmentDate.value.text
            ))

            _hospitalState.value = hospitalState.value.copy(isLoading = false)

            when (result){
                is Resource.AuthError -> {
                    _hospitalEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }
                is Resource.Error -> {
                    _hospitalEvent.emit(
                        UiEvent.SnackBarEvent(
                            result.message ?: "Gagal Fetch Data"
                        )
                    )
                }
                is Resource.Success -> {
                    _hospitalEvent.emit(
                        UiEvent.NavigateEvent
                    )
                }
            }
        }
    }
}