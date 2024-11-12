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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun HospitalScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: HospitalViewModel = hiltViewModel(),
    onNavigateToDoctorAppointment: (String) -> Unit,
    onNavigateToDoctorOnDuty: (String) -> Unit,
    navController: NavHostController
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.hospitalEvent.collectLatest { event ->
            when (event) {
                is UiEvent.SnackBarEvent -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }

                is UiEvent.NavigateInvalidAuthEvent -> {
                    onNavigateToLogin()
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
            BottomBar(
                navController = navController
            )
        }
    ) { padding ->
        Box (
            modifier = Modifier.padding(padding)
        ){
            Column {
                Text(
                    text = "Silahkan Pilih Rumah Sakit",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp)
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start
                )
                LazyColumn(
                    state = rememberLazyListState(),
                ) {
                    items(viewModel.hospitalList.value) { item ->
                        HospitalCard(
                            hospital = item,
                            onChooseAppointment = onNavigateToDoctorAppointment,
                            onChooseOnDuty = onNavigateToDoctorOnDuty
                        )
                    }
                }
            }

            if (viewModel.hospitalState.value.isLoading) {
                LoadingModal()
            }
        }
    }
}