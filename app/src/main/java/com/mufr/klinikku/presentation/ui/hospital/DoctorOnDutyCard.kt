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
import com.mufr.klinikku.domain.entities.doctor.DoctorOnDuty
import com.mufr.klinikku.presentation.ui.theme.LightGrey

@Composable
fun DoctorOnDutyCard(
    onChooseDoctor: (String)->Unit,
    doctorOnDuty: DoctorOnDuty
){
    Row (
        Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = LightGrey)
            .clickable {
                onChooseDoctor(doctorOnDuty.id)
            },
    ){
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(0.35f)
                .height(100.dp),
            model = doctorOnDuty.image,
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
            text = doctorOnDuty.name,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp ,
            color = Color.Black
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                text = doctorOnDuty.specialization,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp ,
                color = Color.Black
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                text = "${doctorOnDuty.startHour} - ${doctorOnDuty.endHour}" ,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp ,
                color = Color.Gray
            )

        }
    }
}