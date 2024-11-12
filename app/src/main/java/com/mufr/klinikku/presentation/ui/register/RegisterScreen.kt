package com.mufr.klinikku.presentation.ui.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import com.mufr.klinikku.presentation.ui.modal.DatePickerModal
import com.mufr.klinikku.presentation.ui.modal.LoadingModal
import com.mufr.klinikku.presentation.ui.modal.convertMillisToDate
import com.mufr.klinikku.shared.Constant
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {
    val snackBarHostState = remember{SnackbarHostState()}
    val focusManager = LocalFocusManager.current
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    val radioOptions = listOf(Constant.GENDER_MALE , Constant.GENDER_FEMALE)
    val showDatePicker =  remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true){
        viewModel.registerEvent.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateEvent -> {
                    onNavigateToLogin()
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
    ) {
        Box {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            ) {
                Text(
                    text = "Selamat Datang 👋",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Silahkan registrasi",
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

                Text(
                    text = "Nama",
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
                    value = viewModel.username.value.text,
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
                        viewModel.setUsername(it)
                    },
                    placeholder = { Text(text = "Masukan username")},
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
                    text = "Tanggal Lahir",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start
                )

                OutlinedTextField(
                    value = viewModel.dateOfBirth.value.text,
                    onValueChange = { },
                    label = { Text("Tanggal Lahir") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker.value = !showDatePicker.value }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                )
                
                if (showDatePicker.value){
                    DatePickerModal(
                        onDateSelected = {it ->
                            viewModel.setDateOfBirth(convertMillisToDate(it!!))
                        },
                        onDismiss = {
                            showDatePicker.value = !showDatePicker.value
                        }
                    )
                }

                Text(
                    text = "Jenis Kelamin",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(bottom = 10.dp, top = 20.dp)
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start
                )
                radioOptions.forEach{ text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == viewModel.gender.value.value),
                                onClick = {
                                    viewModel.setGender(text)
                                }
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == viewModel.gender.value.value),
                            onClick = {
                                viewModel.setGender(text)
                            }
                        )
                        Text(
                            text = text,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

                Text(
                    text = "Nomor Telphone",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(bottom = 10.dp, top = 10.dp)
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start
                )

                TextField(
                    value = viewModel.contactNumber.value.text,
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
                        viewModel.setContactNumber(it)
                    },
                    placeholder = { Text(text = "Masukan username")},
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
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
                        viewModel.register()
                    },
                ) {
                    Text(text = "Register")
                }

            }

            if (viewModel.registerState.value.isLoading){
                LoadingModal()
            }
        }

    }

}