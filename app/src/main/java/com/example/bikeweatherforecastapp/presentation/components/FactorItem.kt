package com.example.bikeweatherforecastapp.presentation.components

import android.R.attr.fontWeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikeweatherforecastapp.domain.model.BikeRidingFactor


@Composable
fun FactorItem(
    factor: BikeRidingFactor,
    heigh: Dp
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF334155).copy(0.6f)
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(80.dp)
            .height(heigh)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
        ) {
            Text(
                text = factor.icon,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text=factor.name,
                color=Color.White,
                fontSize = 10.sp,
                fontWeight= FontWeight.Medium,
                maxLines=1,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(4.dp))
            Text(
                text=factor.description,
                color=Color(0xFF94A3B8),
                fontSize = 8.sp,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 10.sp
            )
        }
    }
}