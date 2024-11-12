package com.mufr.klinikku.presentation.ui.patient

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mufr.klinikku.presentation.ui.UiEvent
import com.mufr.klinikku.presentation.ui.bottombar.BottomBar
import com.mufr.klinikku.presentation.ui.modal.LoadingModal
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientScreen(
    viewModel: PatientViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    navController: NavHostController
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val showChoosePatientFormModal = remember {
        mutableStateOf(false)
    }
    val patientFormModalMode = remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        viewModel.patientEvent.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateInvalidAuthEvent -> {
                    onNavigateToLogin()
                }

                is UiEvent.SnackBarEvent -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }

                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { padding ->
        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ){
                Text(
                    text = "Daftar Pasien",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp)
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start
                )

                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    items(viewModel.patientList.value) { item ->
                        PatientCard(
                            patient = item,
                            onEdit = {
                                patientFormModalMode.value = "Edit Pasien"
                                viewModel.setPatient(it)
                                viewModel.setShowPatientFormModal()
                            }
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(height = 40.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFF94917)
                    ),
                    onClick = {
                        patientFormModalMode.value = "Tambah Pasien"
                        viewModel.setShowPatientFormModal()
                    },
                ) {
                    Text(text = "Tambah Pasien")
                }
            }

            if (viewModel.showPatientFormModal.value) {
                PatientFormModal(
                    mode = patientFormModalMode.value,
                    onClick = {
                        if (patientFormModalMode.value == "Tambah Pasien"){
                            viewModel.addPatient(it)
                        }else{
                            viewModel.editPatient(it)
                        }

                    },
                    onDismiss = {
                        showChoosePatientFormModal.value = !showChoosePatientFormModal.value
                    },
                    patient = viewModel.editPatient.value
                )
            }

            if (viewModel.patientState.value.isLoading) {
                LoadingModal()
            }
        }
    }
}