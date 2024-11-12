package com.mufr.klinikku.presentation.ui.history

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mufr.klinikku.domain.entities.appointment.Appointment

@Composable
fun AppointmentCard(
    appointment: Appointment,
    onClick: (Appointment) -> Unit
){
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(15.dp)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ){
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = "Waktu temu : "
            )
            Text(
                text = appointment.time ?: ""
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ){
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = "Dokter : "
            )
            Text(
                text = appointment.doctorName ?: ""
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ){
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = "Pasien : "
            )
            Text(
                text = appointment.patientName ?: ""
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(top = 5.dp)
                .height(height = 40.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                Color(0xFFF94917)
            ),
            onClick = {
                onClick(appointment)
            },
        ) {
            Text(text = "Ganti Waktu")
        }
    }
}