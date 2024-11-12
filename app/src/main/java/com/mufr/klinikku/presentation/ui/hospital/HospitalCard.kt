package com.mufr.klinikku.presentation.ui.hospital

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.mufr.klinikku.domain.entities.hospital.Hospital
import com.mufr.klinikku.presentation.ui.theme.LightGrey

@Composable
fun HospitalCard(
    hospital: Hospital,
    onChooseAppointment: (String) -> Unit,
    onChooseOnDuty: (String) -> Unit
) {
    Column(
        Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = LightGrey),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            model = hospital.image,
            contentDescription = "Hospital Image",
            error = painterResource(id = R.drawable.picture_placeholder),
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            text = hospital.name,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp ,
            color = Color.Black
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            text = hospital.address,
            fontSize = 12.sp ,
            color = Color.Gray
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            text = hospital.contactNumber,
            fontSize = 12.sp ,
            color = Color.Gray
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ){
            Button(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .weight(1f),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFF94917)
                ),
                onClick = {
                    onChooseAppointment(hospital.id)
                },
            ) {
                Text(text = "Buat Janji")
            }
            
            Spacer(modifier = Modifier.width(10.dp))
            
            Button(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .weight(1f),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFF94917)
                ),
                onClick = {
                    onChooseOnDuty(hospital.id)
                },
            ) {
                Text(text = "Ambil Antrian")
            }
        }
    }
}
