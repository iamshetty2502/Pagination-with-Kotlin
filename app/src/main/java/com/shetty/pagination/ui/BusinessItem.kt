package com.shetty.pagination.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.shetty.pagination.R
import com.shetty.pagination.domain.model.Restaurant
import com.shetty.pagination.utils.Constants
import kotlin.math.abs

@Composable
fun BusinessItem(
    business: Restaurant,
    onClick: () -> Unit
) {
    val defaultImages = listOf(R.drawable.default_1, R.drawable.default_2, R.drawable.default_3)
    val defaultImage = defaultImages[abs(business.id.hashCode()) % defaultImages.size]

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = if (business.imageUrl.isEmpty()) defaultImage else business.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(defaultImage),
            error = painterResource(defaultImage)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (business.isOpen) Color.Green else Color.Red)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = business.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            if (business.address.isNotEmpty()) {
                Text(
                    text = business.address,
                    fontSize = 16.sp
                )
            }
            
            Text(
                text = "${Constants.AVAILABILITY_STATUS} ${
                    if (!business.isOpen) Constants.CURRENTLY_CLOSED else Constants.CURRENTLY_OPEN
                }",
                fontSize = 14.sp
            )
        }
    }
}
