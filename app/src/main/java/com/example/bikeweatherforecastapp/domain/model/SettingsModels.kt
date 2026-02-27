package com.example.bikeweatherforecastapp.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bikeweatherforecastapp.ui.theme.CyanAccent
import com.example.bikeweatherforecastapp.ui.theme.TextTertiary

data class Setting(
    val icon: ImageVector,
    val title: String,
    val subtitle: String? = null,
    val iconTint: Color = CyanAccent,
    val actionIcon: ImageVector? = null,
    val actionIconTint: Color = TextTertiary,
    val onClick: () -> Unit
)