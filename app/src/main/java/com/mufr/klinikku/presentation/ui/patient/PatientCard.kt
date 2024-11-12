package com.mufr.klinikku.presentation.ui.patient

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
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
import com.mufr.klinikku.domain.entities.patient.Patient

@Composable
fun PatientCard(
    patient: Patient,
    onEdit: (Patient) -> Unit
){
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .border(1.dp , Color.Black, RoundedCornerShape(10.dp))
            .padding(15.dp)
    ) {
        Text(text = patient.name)
        Text(text = patient.dateOfBirth)
        Text(text = patient.gender)
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
                onEdit(patient)
            },
        ) {
            Text(text = "Edit Pasien")
        }
    }
}