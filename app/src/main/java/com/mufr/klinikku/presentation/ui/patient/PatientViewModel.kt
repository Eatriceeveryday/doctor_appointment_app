package com.mufr.klinikku.presentation.ui.patient

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.patient.AddPatientRequest
import com.mufr.klinikku.domain.entities.patient.Patient
import com.mufr.klinikku.domain.use_cases.patient.AddPatientUseCase
import com.mufr.klinikku.domain.use_cases.patient.EditPatientUseCase
import com.mufr.klinikku.domain.use_cases.patient.GetAllPatientUseCase
import com.mufr.klinikku.presentation.state.UiState
import com.mufr.klinikku.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(
    private val getAllPatientUseCase: GetAllPatientUseCase,
    private val addPatientUseCase: AddPatientUseCase,
    private val editPatientUseCase: EditPatientUseCase
) : ViewModel() {
    private var _patientState = mutableStateOf(UiState())
    val patientState: State<UiState> = _patientState

    private var _patientEvent = MutableSharedFlow<UiEvent>()
    val patientEvent = _patientEvent

    private var _patientList = mutableStateOf(mutableStateListOf<Patient>())
    val patientList = _patientList

    private var _editPatient = mutableStateOf(
        Patient(
            name = "",
            gender = "",
            dateOfBirth = ""
        )
    )
    val editPatient = _editPatient

    fun setPatient(patient: Patient) {
        _editPatient.value = editPatient.value.copy(
            patientId = patient.patientId,
            name = patient.name,
            gender = patient.gender,
            dateOfBirth = patient.dateOfBirth
        )
    }

    private var _showPatientFormModal = mutableStateOf(false)
    val showPatientFormModal = _showPatientFormModal

    fun setShowPatientFormModal() {
        _showPatientFormModal.value = !showPatientFormModal.value
    }

    init {
        getPatient()
    }

    private fun getPatient() {
        viewModelScope.launch {
            _patientState.value = patientState.value.copy(isLoading = true)

            val result = getAllPatientUseCase()

            _patientState.value = patientState.value.copy(isLoading = false)

            Log.d("Check Result : ", result.toString())

            when (result) {
                is Resource.AuthError -> {
                    _patientEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }

                is Resource.Error -> {
                    _patientEvent.emit(
                        UiEvent.SnackBarEvent(result.message ?: "Gagal mendapatkan data pasien")
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

    fun addPatient(patient: Patient) {
        viewModelScope.launch {
            _patientState.value = patientState.value.copy(isLoading = true)

            val result = addPatientUseCase(
                AddPatientRequest(
                    name = patient.name,
                    dateOfBirth = patient.dateOfBirth,
                    gender = patient.gender
                )
            )

            _patientState.value = patientState.value.copy(isLoading = false)

            when (result.result) {
                is Resource.AuthError -> {
                    _patientEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }

                is Resource.Error -> {
                    _patientEvent.emit(
                        UiEvent.SnackBarEvent(result.result.message ?: "Gagal menambahkan pasien")
                    )
                }

                is Resource.Success -> {
                    setShowPatientFormModal()
                    _patientList.value.clear()
                    getPatient()
                    setPatient(
                        Patient(
                            patientId = "",
                            name = "",
                            gender = "",
                            dateOfBirth = ""
                        )
                    )
                }

                null -> {}
            }

            if (result.error != null) {
                _patientEvent.emit(
                    UiEvent.SnackBarEvent(result.error)
                )
            }
        }
    }

    fun editPatient(patient: Patient) {
        viewModelScope.launch {
            _patientState.value = patientState.value.copy(isLoading = true)

            val result = editPatientUseCase(
                patientId = patient.patientId ?: "",
                request = AddPatientRequest(
                    name = patient.name,
                    gender = patient.gender,
                    dateOfBirth = patient.dateOfBirth
                )
            )

            _patientState.value = patientState.value.copy(isLoading = false)

            when (result.result) {
                is Resource.AuthError -> {
                    _patientEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }

                is Resource.Error -> {
                    _patientEvent.emit(
                        UiEvent.SnackBarEvent(result.result.message ?: "Gagal menambahkan pasien")
                    )
                }

                is Resource.Success -> {
                    setShowPatientFormModal()
                    _patientList.value.clear()
                    getPatient()
                }

                null -> {}
            }

            if (result.error != null) {
                _patientEvent.emit(
                    UiEvent.SnackBarEvent(result.error)
                )
            }
        }
    }
}