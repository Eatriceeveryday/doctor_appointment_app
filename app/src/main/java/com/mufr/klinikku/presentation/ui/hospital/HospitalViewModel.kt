package com.mufr.klinikku.presentation.ui.hospital

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.entities.hospital.Hospital
import com.mufr.klinikku.domain.use_cases.hospital.GetHospitalUseCase
import com.mufr.klinikku.presentation.state.UiState
import com.mufr.klinikku.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HospitalViewModel @Inject constructor(
    private val getHospitalUseCase: GetHospitalUseCase
) : ViewModel() {
    private var _hospitalState = mutableStateOf(UiState())
    val hospitalState: State<UiState> = _hospitalState

    private var _hospitalEvent = MutableSharedFlow<UiEvent>()
    val hospitalEvent = _hospitalEvent

    private var _hospitalList = mutableStateOf(mutableStateListOf<Hospital>())
    val hospitalList = _hospitalList

    init {
        getHospital()
    }

    private fun getHospital() {
        viewModelScope.launch {
            _hospitalState.value = hospitalState.value.copy(isLoading = true)

            val result = getHospitalUseCase()

            _hospitalState.value = hospitalState.value.copy(isLoading = false)

            when (result) {
                is Resource.AuthError -> {
                    hospitalEvent.emit(
                        UiEvent.NavigateInvalidAuthEvent
                    )
                }

                is Resource.Error -> {
                    hospitalEvent.emit(
                        UiEvent.SnackBarEvent(result.message!!)
                    )
                }

                is Resource.Success -> {
                    result.data?.let { _hospitalList.value.addAll(it) }
                }
            }

        }
    }

}