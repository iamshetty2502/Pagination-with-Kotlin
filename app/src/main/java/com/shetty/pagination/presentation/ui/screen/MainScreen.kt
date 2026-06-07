package com.shetty.pagination.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.shetty.pagination.domain.model.Restaurant
import com.shetty.pagination.presentation.state.MainEvent
import com.shetty.pagination.presentation.state.MainUiState
import com.shetty.pagination.presentation.viewmodels.MainViewModel
import java.util.Locale

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onNavigateToDetail: (Restaurant) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var sliderValue by remember { mutableFloatStateOf(0f) }

    val searchRadius = when (val state = uiState) {
        is MainUiState.Success -> state.radius
        else -> 0
    }

    val pagingItems = remember(searchRadius) {
        viewModel.getRestaurantsInProvidedRadius(searchRadius)
    }.collectAsLazyPagingItems()


    Column(modifier = Modifier.fillMaxSize()) {
        // Map integration above the radius selector
        RadiusSelector(
            radius = sliderValue,
            onRadiusChange = { sliderValue = it },
            onRadiusChangeFinished = {
                viewModel.onEvent(MainEvent.UpdateRadius(sliderValue.toInt()))
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { it.id },
                contentType = pagingItems.itemContentType { "business" }
            ) { index ->
                val business = pagingItems[index]
                if (business != null) {
                    BusinessItem(
                        business = business,
                        onClick = {
                            onNavigateToDetail(business)
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun RadiusSelector(
    radius: Float,
    onRadiusChange: (Float) -> Unit,
    onRadiusChangeFinished: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Radius Selector",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (radius < 1000) {
                    "${radius.toInt()}m"
                } else {
                    String.format(Locale.getDefault(), "%.2fKm", radius / 1000f)
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Slider(
            value = radius,
            onValueChange = onRadiusChange,
            onValueChangeFinished = onRadiusChangeFinished,
            valueRange = 0f..5000f,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "0m", fontWeight = FontWeight.Bold)
            Text(text = "5km", fontWeight = FontWeight.Bold)
        }
    }
}