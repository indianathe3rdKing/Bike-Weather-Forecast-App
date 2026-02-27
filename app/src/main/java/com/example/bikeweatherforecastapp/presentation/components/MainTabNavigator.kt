package com.example.bikeweatherforecastapp.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.bikeweatherforecastapp.ui.icons.HeroIcons
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.bikeweatherforecastapp.domain.model.WeatherResponse
import com.example.bikeweatherforecastapp.presentation.screens.SettingsScreen
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel
import com.example.bikeweatherforecastapp.ui.theme.CardBackground
import com.example.bikeweatherforecastapp.ui.theme.CardBackgroundBest
import com.example.bikeweatherforecastapp.ui.theme.CyanAccent
import com.example.bikeweatherforecastapp.ui.theme.FactorBackground
import com.example.bikeweatherforecastapp.ui.theme.Success
import com.example.bikeweatherforecastapp.ui.theme.SuccessLight
import com.example.bikeweatherforecastapp.ui.theme.TextTertiary

@Composable
fun MainTabNavigator(
    weatherData: WeatherResponse,
    viewModel: WeatherViewModel
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var NavColor= CardBackground.copy(0.9f)
    Scaffold(
        containerColor = Color.Transparent,
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                        top =
                            innerPadding.calculateTopPadding(),
                        end = innerPadding.calculateEndPadding(
                            LayoutDirection.Ltr
                        ),
                        bottom = 0.dp
                    )
                    .fillMaxSize()


            ) {
                when (selectedTabIndex) {
                    0 -> WeatherContent(weatherData, viewModel)
                    1 -> SettingsScreen()
                }
            }
        },
        bottomBar = {
            Surface(
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = CardBackground
            ) {
                NavigationBar(
                    containerColor = NavColor,
                    tonalElevation = 2.dp,
                    modifier = Modifier.shadow(2.dp)
                        .border(
                            1.dp,
                            TextTertiary.copy(0.5f),
                            RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp)
                        )
                ) {
                    TabNavigationItem(
                        title = "Home",
                        icon = HeroIcons.Home,
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 }
                    )
                    TabNavigationItem(
                        title = "Settings",
                        icon = HeroIcons.Cog6Tooth,
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 }
                    )
                }
            }
        }
    )
}

@Composable
private fun RowScope.TabNavigationItem(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = title
            )
        },
        label = { Text(title) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Success,
            selectedTextColor = SuccessLight,
            unselectedIconColor = TextTertiary,
            unselectedTextColor = TextTertiary,
            indicatorColor = FactorBackground
        )
    )
}
