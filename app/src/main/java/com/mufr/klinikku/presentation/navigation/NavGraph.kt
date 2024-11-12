package com.mufr.klinikku.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mufr.klinikku.presentation.ui.history.HistoryScreen
import com.mufr.klinikku.presentation.ui.hospital.HospitalDoctorAppointmentScreen
import com.mufr.klinikku.presentation.ui.hospital.HospitalDoctorOnDutyScreen
import com.mufr.klinikku.presentation.ui.hospital.HospitalScreen
import com.mufr.klinikku.presentation.ui.login.LoginScreen
import com.mufr.klinikku.presentation.ui.patient.PatientScreen
import com.mufr.klinikku.presentation.ui.register.RegisterScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.Hospital.route) {
        composable(route = Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(route = Screen.Login.route) }
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(route = Screen.Register.route) },
                onNavigateToHospital = { navController.navigate(route = Screen.Hospital.route) }
            )
        }

        composable(route = Screen.Hospital.route) {
            HospitalScreen(
                onNavigateToLogin = {
                    navController.navigate(route = Screen.Login.route) {
                        popUpTo(Screen.Hospital.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToDoctorAppointment = { hospitalId ->
                    navController.navigate(
                        route = Screen.HospitalDoctorAppointment.createRoute(
                            hospitalId
                        )
                    )
                },
                onNavigateToDoctorOnDuty = { hospitalId ->
                    navController.navigate(
                        route = Screen.HospitalDoctorOnDuty.createRoute(
                            hospitalId
                        )
                    )
                },
                navController = navController
            )
        }

        composable(
            route = Screen.HospitalDoctorAppointment.route,
            arguments = listOf(navArgument("hospitalId") { type = NavType.StringType })
        ) {
            HospitalDoctorAppointmentScreen(
                onNavigateToLogin = {
                    navController.navigate(route = Screen.Login.route) {
                        popUpTo(Screen.HospitalDoctorAppointment.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToHospital = {
                    navController.navigate(route = Screen.Hospital.route) {
                        popUpTo(Screen.HospitalDoctorAppointment.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.HospitalDoctorOnDuty.route,
            arguments = listOf(navArgument("hospitalId") { type = NavType.StringType })
        ) {
            HospitalDoctorOnDutyScreen(
                onNavigateToLogin = {
                    navController.navigate(route = Screen.Login.route) {
                        popUpTo(Screen.HospitalDoctorAppointment.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToHospital = {
                    navController.navigate(route = Screen.Hospital.route) {
                        popUpTo(Screen.HospitalDoctorAppointment.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = Screen.Profile.route) {
            PatientScreen(
                onNavigateToLogin = {
                    navController.navigate(route = Screen.Login.route) {
                        popUpTo(Screen.HospitalDoctorAppointment.route) {
                            inclusive = true
                        }
                    }
                },
                navController = navController
            )
        }

        composable(route = Screen.History.route) {
            HistoryScreen(
                onNavigateToLogin = {
                    navController.navigate(route = Screen.Login.route) {
                        popUpTo(Screen.HospitalDoctorAppointment.route) {
                            inclusive = true
                        }
                    }
                },
                navController = navController
            )
        }
    }
}