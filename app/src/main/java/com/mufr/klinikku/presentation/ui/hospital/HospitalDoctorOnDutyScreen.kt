package com.mufr.klinikku.presentation.ui.hospital

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mufr.klinikku.presentation.ui.UiEvent
import com.mufr.klinikku.presentation.ui.modal.ChoosePatientModal
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HospitalDoctorOnDutyScreen(
    viewModel: HospitalDoctorOnDutyViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToHospital: () -> Unit
){
    val snackBarHostState = remember { SnackbarHostState() }
    val showChoosePatientModal = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.hospitalEvent.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateEvent -> {
                    onNavigateToHospital()
                }

                is UiEvent.SnackBarEvent -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }

                is UiEvent.NavigateInvalidAuthEvent -> {
                    onNavigateToLogin()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ){
        Box{
            Column {
                Text(
                    text = "Silahkan Pilih Dokter",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp)
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start
                )
                LazyColumn(
                    state = rememberLazyListState(),
                ){
                    items(viewModel.doctorList.value){item ->
                        DoctorOnDutyCard(
                            onChooseDoctor = {
                                viewModel.setOnDutyId(it)
                                showChoosePatientModal.value = !showChoosePatientModal.value
                            },
                            doctorOnDuty = item)
                    }
                }
            }

            if (showChoosePatientModal.value){
                ChoosePatientModal(
                    patients = viewModel.patientList.value,
                    onChoosePatient = {
                        viewModel.setPatientId(it)
                        viewModel.addToQueue()
                    },
                    onDismiss = {
                        showChoosePatientModal.value = !showChoosePatientModal.value
                    })
            }

        }
    }
}