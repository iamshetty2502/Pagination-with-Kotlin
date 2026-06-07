package com.shetty.pagination.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.shetty.pagination.R
import com.shetty.pagination.presentation.viewmodels.MainViewModel
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit,
    mainViewModel: MainViewModel
) {
    val restaurant by mainViewModel.selectedRestaurant.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            restaurant?.let { rest ->
                val defaultImages =
                    listOf(R.drawable.default_1, R.drawable.default_2, R.drawable.default_3)
                val defaultImage = defaultImages[abs(rest.id.hashCode()) % defaultImages.size]

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = if (rest.imageUrl.isEmpty()) defaultImage else rest.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(defaultImage),
                        error = painterResource(defaultImage)
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(if (rest.isOpen) Color.Green else Color.Red)
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = rest.name,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            // Column ensures address is directly below the label
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(0.dp) // Tight vertical spacing
                            ) {
                                Text(
                                    text = "Description / Address:",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = rest.address,
                                    fontSize = 16.sp,
                                    lineHeight = 20.sp // Ensures predictable line spacing
                                )
                            }
                            Button(
                                onClick = { /* Action */ },
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text("Book Now")
                            }
                        }

                        if (rest.phone.isNotBlank() && rest.phone != "none") {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Contact Info:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = rest.phone,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            } ?: run {
                // Error state if data is missing
                Text(
                    text = "Restaurant data not found",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
