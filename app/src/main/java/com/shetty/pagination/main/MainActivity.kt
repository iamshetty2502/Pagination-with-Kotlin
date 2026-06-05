package com.shetty.pagination.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.shetty.pagination.ui.BusinessItem
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    // sliderValue updates immediately as the user drags for UI feedback
    var sliderValue by remember { mutableFloatStateOf(0f) }

    val searchRadius = when (val state = uiState) {
        is MainUiState.Success -> state.radius
        else -> 0
    }

    val pagingItems = remember(searchRadius) {
        viewModel.getRestaurantsInProvidedRadius(searchRadius)
    }.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
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
                key = pagingItems.itemKey { it.id ?: "" },
                contentType = pagingItems.itemContentType { "business" }
            ) { index ->
                val business = pagingItems[index]
                if (business != null) {
                    BusinessItem(business = business)
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
