package com.example.bikeweatherforecastapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikeweatherforecastapp.presentation.components.SettingsActionItem
import com.example.bikeweatherforecastapp.presentation.components.SettingsToggleItem
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel
import com.example.bikeweatherforecastapp.ui.theme.CardBackground
import com.example.bikeweatherforecastapp.ui.theme.CyanAccent
import com.example.bikeweatherforecastapp.ui.theme.TextPrimary
import com.example.bikeweatherforecastapp.ui.theme.TextSecondary
import com.example.bikeweatherforecastapp.ui.theme.TextTertiary
import com.example.bikeweatherforecastapp.ui.theme.Warning
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(viewModel: WeatherViewModel=koinViewModel()) {
    // State for toggle settings
    val weatherState by viewModel.weatherState
    val isMetricUnit = weatherState.isMetric
    var notificationsEnabled by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .padding(bottom = 100.dp) // Extra padding for bottom navigation bar
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Header
        Text(
            text = "Settings",
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Customize your app experience",
            color = TextTertiary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Units Section
        SettingsSectionHeader(title = "Units")

        Spacer(modifier = Modifier.height(12.dp))

        // Unit Toggle Card with custom implementation for toggle buttons
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = CardBackground.copy(alpha = 0.6f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Units",
                        tint = CyanAccent,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Column {
                        Text(
                            text = "Temperature Unit",
                            color = TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (isMetricUnit) "Celsius (°C)" else "Fahrenheit (°F)",
                            color = TextTertiary,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Toggle Buttons for Unit Selection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    UnitToggleButton(
                        text = "Metric (°C)",
                        isSelected = isMetricUnit,
                        onClick = { viewModel.updateUnit(true) },
                        modifier = Modifier.weight(1f)
                    )
                    UnitToggleButton(
                        text = "Imperial (°F)",
                        isSelected = !isMetricUnit,
                        onClick = { viewModel.updateUnit(false) },
                        modifier = Modifier.weight(1f)

                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Notifications Section
        SettingsSectionHeader(title = "Notifications")

        Spacer(modifier = Modifier.height(12.dp))

        SettingsToggleItem(
            icon = Icons.Default.Notifications,
            title = "Daily Forecast Alerts",
            subtitle = "Get notified about best riding conditions",
            checked = notificationsEnabled,
            onCheckedChange = { notificationsEnabled = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Premium Section
        SettingsSectionHeader(title = "Premium")

        Spacer(modifier = Modifier.height(12.dp))

        SettingsActionItem(
            icon = Icons.Default.Star,
            title = "Remove Ads",
            subtitle = "Enjoy an ad-free experience",
            iconTint = Warning,
            actionIcon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            onClick = {
                // Handle remove ads action - navigate to purchase screen
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // About Section
        SettingsSectionHeader(title = "About")

        Spacer(modifier = Modifier.height(12.dp))

        SettingsActionItem(
            icon = Icons.Default.Build,
            title = "App Version",
            subtitle = "1.0.0",
            actionIcon = null,
            onClick = { }
        )

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        color = CyanAccent,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 4.dp)
    )
}

@Composable
private fun UnitToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) CyanAccent else CardBackground,
            contentColor = if (isSelected) Color.Black else TextSecondary
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}








