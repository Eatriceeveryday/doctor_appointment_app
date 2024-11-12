package com.mufr.klinikku.presentation.navigation

sealed class Screen(val route: String){
    object Register: Screen(route = "register")
    object Login: Screen(route = "login")
    object Hospital: Screen(route = "hospital")
    object HospitalDoctorAppointment: Screen(route = "hospital/appointment/{hospitalId}"){
        fun createRoute(hospitalId: String) = "hospital/appointment/${hospitalId}"
    }
    object Profile: Screen(route = "profile")
    object HospitalDoctorOnDuty: Screen(route = "hospital/on-duty/{hospitalId}"){
        fun createRoute(hospitalId: String) = "hospital/on-duty/${hospitalId}"
    }
    object History: Screen(route = "history")
}
