package com.mufr.klinikku.presentation.ui.register

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufr.klinikku.domain.entities.auth.RegisterRequest
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.use_cases.auth.RegisterUseCase
import com.mufr.klinikku.presentation.navigation.Screen
import com.mufr.klinikku.presentation.state.DateState
import com.mufr.klinikku.presentation.state.RadioButtonState
import com.mufr.klinikku.presentation.state.TextFieldState
import com.mufr.klinikku.presentation.ui.UiEvent
import com.mufr.klinikku.presentation.state.UiState
import com.mufr.klinikku.shared.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
):ViewModel(){
    private var _registerState = mutableStateOf(UiState())
    val registerState: State<UiState> = _registerState

    private var _registerEvent = MutableSharedFlow<UiEvent>()
    val registerEvent = _registerEvent

    private var _email = mutableStateOf(TextFieldState())
    val email: MutableState<TextFieldState> = _email

    fun setEmail(value: String){
        _email.value = email.value.copy(text = value)
    }

    private var _password = mutableStateOf(TextFieldState())
    val password: MutableState<TextFieldState> = _password

    fun setPassword(value: String){
        _password.value = password.value.copy(text = value)
    }

    private var _username = mutableStateOf(TextFieldState())
    val username: MutableState<TextFieldState> = _username

    fun setUsername(value: String){
        _username.value = username.value.copy(text = value)
    }

    private var _dateOfBirth = mutableStateOf(TextFieldState())
    val dateOfBirth: State<TextFieldState> = _dateOfBirth

    fun setDateOfBirth(date: String){
        _dateOfBirth.value = dateOfBirth.value.copy(text = date)
    }

    private var _gender = mutableStateOf(RadioButtonState(""))
    val gender: MutableState<RadioButtonState> = _gender

    fun setGender(value: String){
        _gender.value = gender.value.copy(value = value)
    }

    private var _contactNumber = mutableStateOf(TextFieldState())
    val contactNumber: MutableState<TextFieldState> = _contactNumber

    fun setContactNumber(value: String){
        _contactNumber.value = contactNumber.value.copy(text = value)
    }

    fun register(){
        viewModelScope.launch {
            _registerState.value = registerState.value.copy(isLoading = true)

            val result = registerUseCase(
                RegisterRequest(
                    email = email.value.text,
                    password = password.value.text,
                    username = username.value.text,
                    gender = gender.value.value,
                    contactNumber = contactNumber.value.text,
                    dateOfBirth = dateOfBirth.value.text

                )
            )

            _registerState.value = registerState.value.copy(isLoading = false)

            when (result.result){
                is Resource.Error -> {
                    Log.d("Resource Value : " , result.toString())
                    println(result.error)
                    _registerEvent.emit(
                        UiEvent.SnackBarEvent(
                            result.result.message ?: "Err not found"
                        )
                    )
                }
                is Resource.Success -> {
                    println(result.error)
                    _registerEvent.emit(
                        UiEvent.NavigateEvent
                    )
                }
                else -> {}
            }

            if (!result.error.isNullOrEmpty()){
                Log.d("Result Value : " , result.toString())
                _registerEvent.emit(
                    UiEvent.SnackBarEvent(
                        result.error.toString()
                    )
                )
            }

        }
    }


}