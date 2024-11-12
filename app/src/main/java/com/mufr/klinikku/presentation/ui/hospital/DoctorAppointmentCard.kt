package com.mufr.klinikku.presentation.ui.hospital

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mufr.klinikku.R
import com.mufr.klinikku.domain.entities.doctor.Doctor
import com.mufr.klinikku.presentation.ui.theme.LightGrey

@Composable
fun DoctorAppointmentCard(
    doctor: Doctor,
    onChooseDoctor: (String)->Unit
){
    Row (
        Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = LightGrey)
            .clickable {
                       onChooseDoctor(doctor.id)
            },
    ){
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(0.35f)
                .height(100.dp),
            model = doctor.image,
            contentDescription = "Hospital Image",
            error = painterResource(id = R.drawable.picture_placeholder),
            contentScale = ContentScale.Crop
        )

        Column (
            Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = LightGrey),
        ){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                text = doctor.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp ,
                color = Color.Black
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                text = doctor.specialization,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp ,
                color = Color.Black
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                text = doctor.hour,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp ,
                color = Color.Gray
            )

        }
    }
}

/*@Preview
@Composable
fun DoctorCardPreview(){
    DoctorAppointmentCard(doctor = Doctor(
        "1",
        name = "Mateto Welch",
        image = "https://img.freepik.com/free-photo/healthcare-workers-preventing-virus-quarantine-campaign-concept-cheerful-friendly-asian-female-physician-doctor-with-clipboard-daily-checkup-standing-white-background_1258-107867.jpg?t=st=1725206635~exp=1725210235~hmac=530ce81a8db49a367f1db2d4ba1478fe11615de60264e182d8f2d2060567eb90",
        hour = "Senin - Jumat, 9 - 12.30"
    ),
        onChooseDoctor = {}
    )
}*/
