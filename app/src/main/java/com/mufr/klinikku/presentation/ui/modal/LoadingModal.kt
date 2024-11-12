package com.mufr.klinikku.presentation.ui.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingModal() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color.White),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp),
                color = Color(0xFFF94917),
                strokeWidth = 1.dp
            )
        }
    }
}