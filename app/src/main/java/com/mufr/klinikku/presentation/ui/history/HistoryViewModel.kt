package com.mufr.klinikku.presentation.ui.history

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufr.klinikku.domain.entities.appointment.Appointment
import com.mufr.klinikku.domain.entities.appointment.ChangeAppointmentRequest
import com.mufr.klinikku.domain.entities.doctor.DoctorAppointmentSchedule
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.queue.Queue
import com.mufr.klinikku.domain.use_cases.appoinment.ChangeAppointmentUseCase
import com.mufr.klinikku.domain.use_cases.appoinment.GetAppointmentUseCase
import com.mufr.klinikku.domain.use_cases.hospital.GetDoctorAppointmentScheduleUseCase
import com.mufr.klinikku.domain.use_cases.queue.GetQueueUseCase
import com.mufr.klinikku.presentation.state.TextFieldState
import com.mufr.klinikku.presentation.state.UiState
import com.mufr.klinikku.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getQueueUseCase: GetQueueUseCase,
    private val getAppointmentUseCase: GetAppointmentUseCase,
    private val getDoctorAppointmentScheduleUseCase: GetDoctorAppointmentScheduleUseCase,
    private val changeAppointmentUseCase: ChangeAppointmentUseCase
): ViewModel(), DefaultLifecycleObserver {
    private var _historyState = mutableStateOf(UiState())
    val historyState: State<UiState> = _historyState

    private var _historyEvent = MutableSharedFlow<UiEvent>()
    val historyEvent = _historyEvent

    private var _queueList = mutableStateOf(mutableStateListOf<Queue>())
    val queueList = _queueList

    private var _appointmentList = mutableStateOf(mutableStateListOf<Appointment>())
    val appointmentList = _appointmentList

    private var _scheduleList = mutableStateOf(mutableStateListOf<DoctorAppointmentSchedule>())
    val scheduleList = _scheduleList

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

    private var _patientId = mutableStateOf(TextFieldState())
    val patientId = _patientId

    fun setPatientId(value: String){
        _patientId.value = patientId.value.copy(text = value)
    }

    private var _scheduleId = mutableStateOf(TextFieldState())
    val scheduleId = _scheduleId

    fun setScheduleId(value: String){
        _scheduleId.value = scheduleId.value.copy(text = value)
    }

    private var _appointmentId = mutableStateOf(TextFieldState())
    val appointmentId = _appointmentId

    fun setAppointmentId(value: String){
        _appointmentId.value = appointmentId.value.copy(text = value)
    }

    init {
        getQueue()
        getAppointment()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d("On Resume : " , "IS Resume")
        getQueue()
        getAppointment()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d("On Pause : " , "IS pause")
    }

    private fun getQueue(){
        viewModelScope.launch {
            _historyState.value = historyState.value.copy(isLoading = true)

            val result = getQueueUseCase()

            _historyState.value = historyState.value.copy(isLoading = false)

            when(result){
                is Resource.AuthError -> {
                    _historyEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }
                is Resource.Error -> {
                    _historyEvent.emit(
                        UiEvent.SnackBarEvent(result.message ?: "Gagal mengambil data")
                    )
                }
                is Resource.Success -> {
                    queueList.value.clear()
                    result.data?.let {
                        _queueList.value.addAll(it)
                    }
                }
            }

        }
    }

    private fun getAppointment(){
        viewModelScope.launch {
            _historyState.value = historyState.value.copy(isLoading = true)

            val result = getAppointmentUseCase()

            _historyState.value = historyState.value.copy(isLoading = false)

            when(result){
                is Resource.AuthError -> {
                    _historyEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }
                is Resource.Error -> {
                    _historyEvent.emit(
                        UiEvent.SnackBarEvent(result.message ?: "Gagal mengambil data")
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

    fun getDoctorAvailability(){
        viewModelScope.launch {
            _historyState.value = historyState.value.copy(isLoading = true)

            Log.d("Check Value of AppointmentDate : " , appointmentDate.value.text)

            val result = getDoctorAppointmentScheduleUseCase(appointmentDate.value.text,doctorId.value.text)

            _historyState.value = historyState.value.copy(isLoading = false)

            when (result){
                is Resource.AuthError -> {
                    _historyEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }
                is Resource.Error -> {
                    _historyEvent.emit(
                        UiEvent.SnackBarEvent(
                            result.message ?: "Gagal Fetch Data"
                        )
                    )
                }
                is Resource.Success -> {
                    _scheduleList.value.clear()
                    result.data?.let {
                        _scheduleList.value.addAll(it)
                    }
                }
            }
        }
    }

    fun changeAppointment(){
        viewModelScope.launch {
            _historyState.value = historyState.value.copy(isLoading = true)

            val result = changeAppointmentUseCase(
                appointmentId.value.text,
                ChangeAppointmentRequest(
                    patientId.value.text,
                    scheduleId.value.text,
                    appointmentDate.value.text
                )
            )

            _historyState.value = historyState.value.copy(isLoading = false)

            when (result.result){
                is Resource.AuthError -> {
                    _historyEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }
                is Resource.Error -> {
                    _historyEvent.emit(
                        UiEvent.SnackBarEvent(
                            result.result.message ?: "Gagal Fetch Data"
                        )
                    )
                }
                is Resource.Success -> {
                    getAppointment()
                }

                null -> {}
            }

            if (result.error != null){
                _historyEvent.emit(
                    UiEvent.SnackBarEvent(
                        result.error
                    )
                )
            }
        }
    }
}