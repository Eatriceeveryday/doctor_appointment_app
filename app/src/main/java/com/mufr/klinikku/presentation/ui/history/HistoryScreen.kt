package com.mufr.klinikku.presentation.ui.history

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mufr.klinikku.presentation.ui.UiEvent
import com.mufr.klinikku.presentation.ui.bottombar.BottomBar
import com.mufr.klinikku.presentation.ui.hospital.DoctorAppointmentAvailabilityModal
import com.mufr.klinikku.presentation.ui.modal.LoadingModal
import com.mufr.klinikku.presentation.ui.util.observeLifecycleEvents
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val tabList = listOf(
        "Janji Temu",
        "Antrian"
    )
    val pagerState = rememberPagerState {
        tabList.size
    }
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val showDoctorAppointmentAvailabilityModal = remember {
        mutableStateOf(false)
    }
    viewModel.observeLifecycleEvents(lifecycle = LocalLifecycleOwner.current.lifecycle)

    LaunchedEffect(key1 = true) {
        viewModel.historyEvent.collectLatest { event ->
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

    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
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
        Box(
            modifier = Modifier.padding(padding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabList.forEachIndexed { index, s ->
                        androidx.compose.material3.Tab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = { Text(text = s, color = Color.Black) }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                ) { index ->
                    if (index == 1) {
                        LazyColumn(
                            state = rememberLazyListState()
                        ) {
                            items(viewModel.queueList.value) { item ->
                                QueueCard(queue = item)
                            }
                        }
                    } else {
                        LazyColumn(state = rememberLazyListState()) {
                            items(viewModel.appointmentList.value) { item ->
                                AppointmentCard(
                                    appointment = item,
                                    onClick = {
                                        viewModel.setDoctorId(it.doctorId ?: "")
                                        viewModel.setPatientId(it.patientId ?: "")
                                        viewModel.setAppointmentId(it.id)
                                        showDoctorAppointmentAvailabilityModal.value =
                                            !showDoctorAppointmentAvailabilityModal.value

                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (showDoctorAppointmentAvailabilityModal.value) {
                DoctorAppointmentAvailabilityModal(
                    onPickDate = { date ->
                        viewModel.setAppointmentDate(date)
                        viewModel.getDoctorAvailability()
                    },
                    date = viewModel.appointmentDate.value.text,
                    schedules = viewModel.scheduleList,
                    onDismiss = {
                        showDoctorAppointmentAvailabilityModal.value =
                            !showDoctorAppointmentAvailabilityModal.value
                    },
                    onChooseSchedule = {
                        viewModel.setScheduleId(it)
                        viewModel.changeAppointment()
                        showDoctorAppointmentAvailabilityModal.value =
                            !showDoctorAppointmentAvailabilityModal.value
                    }
                )
            }

            if (viewModel.historyState.value.isLoading) {
                LoadingModal()
            }
        }
    }
}