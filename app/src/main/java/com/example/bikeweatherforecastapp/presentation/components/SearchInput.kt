package com.example.bikeweatherforecastapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel


@Composable
fun SearchInput(
    viewModel: WeatherViewModel
) {
    var query by remember {
        mutableStateOf("")

    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search City") },
            modifier = Modifier.weight(1f)
        )

        Button(onClick = {
            viewModel.searchCity(query)
        }) {
            Text("Search")

        }
    }
}