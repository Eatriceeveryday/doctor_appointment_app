package com.mufr.klinikku.presentation.ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mufr.klinikku.domain.entities.auth.LoginRequest
import com.mufr.klinikku.domain.entities.general.Resource
import com.mufr.klinikku.domain.use_cases.auth.LoginUseCase
import com.mufr.klinikku.presentation.state.TextFieldState
import com.mufr.klinikku.presentation.state.UiState
import com.mufr.klinikku.presentation.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private var _loginState = mutableStateOf(UiState())
    val loginState: State<UiState> = _loginState

    private var _loginEvent = MutableSharedFlow<UiEvent>()
    val loginEvent = _loginEvent

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

    fun login(){
        viewModelScope.launch {
            _loginState.value = loginState.value.copy(isLoading = true)

            val result = loginUseCase(
                LoginRequest(
                    email = email.value.text,
                    password = password.value.text
                )
            )

            _loginState.value = loginState.value.copy(isLoading = false)

            when (result.result){
                is Resource.Error -> {
                    loginEvent.emit(
                        UiEvent.SnackBarEvent(
                            result.result.message ?: "Login Gagal"
                        )
                    )
                }
                is Resource.Success -> {
                    loginEvent.emit(
                        UiEvent.NavigateEvent
                    )
                }
                else -> {}
            }

            if (result.error != null){
                _loginEvent.emit(
                    UiEvent.SnackBarEvent(result.error)
                )
            }
        }
    }
}