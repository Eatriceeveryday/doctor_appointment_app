package com.mufr.klinikku.presentation.ui.history

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mufr.klinikku.domain.entities.queue.Queue

@Composable
fun QueueCard(
    queue: Queue
){
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(15.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ){
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = "Nama pasien : "
            )
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = queue.patientName
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ){
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = "Nama Dokter : "
            )
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = queue.doctorName
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ){
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = "Urutan : "
            )
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = queue.queueNumber
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ){
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = "Prediksi waktu ketemu : "
            )
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = queue.predictionTime
            )
        }


    }
}