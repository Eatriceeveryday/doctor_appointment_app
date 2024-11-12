package com.mufr.klinikku.presentation.ui.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mufr.klinikku.domain.entities.patient.Patient
import com.mufr.klinikku.presentation.ui.modal.DatePickerModal
import com.mufr.klinikku.presentation.ui.modal.convertMillisToDate
import com.mufr.klinikku.shared.Constant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientFormModal(
    mode: String,
    onClick: (Patient) -> Unit,
    patient: Patient? = null,
    onDismiss: () -> Unit
) {
    val patientName = remember {
        mutableStateOf(patient?.name ?: "")
    }

    val patientDateOfBirth = remember {
        mutableStateOf(patient?.dateOfBirth ?: "")
    }

    val patientGender = remember {
        mutableStateOf(patient?.gender ?: "")
    }

    val radioOptions = listOf(Constant.GENDER_MALE, Constant.GENDER_FEMALE)
    val showDatePicker = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Column(
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
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.End
            )

            Text(
                text = "Nama",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(vertical = 10.dp , horizontal = 20.dp)
                    .align(Alignment.Start),
                textAlign = TextAlign.Start
            )

            TextField(
                value = patientName.value,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.None
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                onValueChange = {
                    patientName.value = it
                },
                placeholder = { Text(text = "Masukan username") },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Text(
                text = "Jenis Kelamin",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(bottom = 10.dp, top = 20.dp, start = 20.dp, end = 20.dp)
                    .align(Alignment.Start),
                textAlign = TextAlign.Start
            )

            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == patientGender.value),
                            onClick = {
                                patientGender.value = text
                            }
                        )
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == patientGender.value),
                        onClick = {
                            patientGender.value = text
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

            OutlinedTextField(
                value = patientDateOfBirth.value,
                onValueChange = { },
                label = { Text("Tanggal Lahir") },
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
                    .padding(horizontal = 20.dp)
                    .height(64.dp)
            )

            Button(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(height = 40.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFF94917)
                ),
                onClick = {
                    onClick(
                        Patient(
                            patientId = patient?.patientId ?: "",
                            name = patientName.value,
                            gender = patientGender.value,
                            dateOfBirth = patientDateOfBirth.value
                        )
                    )
                },
            ) {
                Text(text = mode)
            }

            if (showDatePicker.value) {
                DatePickerModal(
                    onDateSelected = { it ->
                        patientDateOfBirth.value = convertMillisToDate(it)
                    },
                    onDismiss = {
                        showDatePicker.value = !showDatePicker.value
                    }
                )
            }
        }
    }
}