package com.mufr.klinikku.presentation.ui.hospital

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufr.klinikku.domain.entities.doctor.DoctorOnDuty
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.patient.Patient
import com.mufr.klinikku.domain.use_cases.hospital.GetHospitalDoctorOnDutyUseCase
import com.mufr.klinikku.domain.use_cases.patient.GetAllPatientUseCase
import com.mufr.klinikku.domain.use_cases.queue.AddPatientToQueueUseCase
import com.mufr.klinikku.presentation.state.TextFieldState
import com.mufr.klinikku.presentation.state.UiState
import com.mufr.klinikku.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HospitalDoctorOnDutyViewModel @Inject constructor(
    private val getHospitalDoctorOnDutyUseCase: GetHospitalDoctorOnDutyUseCase,
    private val getAllPatientUseCase: GetAllPatientUseCase,
    private val addPatientToQueueUseCase: AddPatientToQueueUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val hospitalId: String = checkNotNull(savedStateHandle["hospitalId"])

    private var _hospitalState = mutableStateOf(UiState())
    val hospitalState: State<UiState> = _hospitalState

    private var _hospitalEvent = MutableSharedFlow<UiEvent>()
    val hospitalEvent = _hospitalEvent

    private var _doctorList = mutableStateOf(mutableStateListOf<DoctorOnDuty>())
    val doctorList = _doctorList

    private var _patientList = mutableStateOf(mutableStateListOf<Patient>())
    val patientList = _patientList

    private var _onDutyId = mutableStateOf(TextFieldState())
    val onDutyId = _onDutyId

    fun setOnDutyId(value: String){
        _onDutyId.value = onDutyId.value.copy(text = value)
    }

    private var _patientId = mutableStateOf(TextFieldState())
    val patientId = _patientId

    fun setPatientId(id: String){
        _patientId.value = patientId.value.copy(text = id)
    }

    init {
        getDoctor()
        getPatient()
    }

    private fun getDoctor(){
        viewModelScope.launch {
            _hospitalState.value = hospitalState.value.copy(isLoading = true)

            val result = getHospitalDoctorOnDutyUseCase(hospitalId)

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

    fun addToQueue(){
        viewModelScope.launch {
            _hospitalState.value = hospitalState.value.copy(isLoading = true)

            val result = addPatientToQueueUseCase(onDutyId.value.text, patientId.value.text)

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