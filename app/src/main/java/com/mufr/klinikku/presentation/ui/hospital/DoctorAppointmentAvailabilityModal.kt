package com.mufr.klinikku.presentation.ui.hospital

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mufr.klinikku.domain.entities.doctor.DoctorAppointmentSchedule
import com.mufr.klinikku.presentation.ui.modal.DatePickerModal
import com.mufr.klinikku.presentation.ui.modal.convertMillisToDate

@Composable
fun DoctorAppointmentAvailabilityModal(
    onPickDate: (String)->Unit,
    date: String,
    schedules: MutableState<SnapshotStateList<DoctorAppointmentSchedule>>,
    onDismiss: ()-> Unit,
    onChooseSchedule: (String) -> Unit
){
    val showDatePicker =  remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ){
        Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .align(Alignment.Center)
            .background(Color.White)
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable {
                        onDismiss()
                    },
                text = "X",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp ,
                color = Color.Black,
                textAlign = TextAlign.End
            )

            OutlinedTextField(
                value = date,
                onValueChange = { },
                label = { Text("Tanggal Temu") },
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
                    .padding(20.dp)
                    .height(64.dp)
                    .background(Color.White)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = "Silahkan pilih waktu temu ",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp ,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ){
                items(schedules.value){item ->
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(20.dp))
                            .border(width = 0.1.dp, color = Color.LightGray)
                            .background(Color.LightGray)
                            .padding(10.dp)
                            .clickable {
                                onChooseSchedule(item.id)
                            }
                    ) {
                        Text(
                            text = "Jam : ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp ,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = item.hour,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp ,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }

            if (showDatePicker.value){
                DatePickerModal(
                    onDateSelected = {
                        Log.d("Check Date value : " , convertMillisToDate(it!!))
                        onPickDate(convertMillisToDate(it!!))
                    },
                    onDismiss = {
                        showDatePicker.value = !showDatePicker.value
                    }
                )
            }

        }
    }


}