package com.mufr.klinikku.presentation.ui.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mufr.klinikku.R
import com.mufr.klinikku.presentation.ui.UiEvent
import com.mufr.klinikku.presentation.ui.modal.LoadingModal
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToHospital: () -> Unit

){
    val snackBarHostState = remember{ SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val passwordVisibility = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true){
        viewModel.loginEvent.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateEvent -> {
                    onNavigateToHospital()
                }
                is UiEvent.SnackBarEvent -> {
                    snackBarHostState.showSnackbar(message = event.message, duration = SnackbarDuration.Short)
                }
                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ){
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            ){
                Text(
                    text = "Selamat Datang 👋",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Silahkan login",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Email",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start
                )

                TextField(
                    value = viewModel.email.value.text,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email,
                        capitalization = KeyboardCapitalization.None
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    onValueChange = {
                        viewModel.setEmail(it)
                    },
                    placeholder = { Text(text = "Masukan email")},
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Text(
                    text = "Password",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start
                )

                TextField(
                    value = viewModel.password.value.text,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password,
                        capitalization = KeyboardCapitalization.None
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    onValueChange = {
                        viewModel.setPassword(it)
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text(text = "Masukan password")},
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisibility.value) {
                            painterResource(id = R.drawable.showpassword)
                        } else {
                            painterResource(id = R.drawable.hidepassword)
                        }
                        IconButton(
                            onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }
                        ) {
                            Icon(painter = image, contentDescription = "password Toggle")
                        }
                    }
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(height = 40.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFF94917)
                    ),
                    onClick = {
                        viewModel.login()
                    },
                ) {
                    Text(text = "Login")
                }

                Text(
                    text = "==Belum punya akun==",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    textAlign = TextAlign.Center
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(height = 40.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFF94917)
                    ),
                    onClick = {
                        onNavigateToRegister()
                    },
                ) {
                    Text(text = "Daftar akun")
                }
            }

            if (viewModel.loginState.value.isLoading){
                LoadingModal()
            }
        }
    }
}