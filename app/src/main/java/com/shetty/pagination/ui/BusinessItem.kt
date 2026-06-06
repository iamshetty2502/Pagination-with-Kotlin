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
import com.shetty.pagination.db.RestaurantEntity
import com.shetty.pagination.utils.Constants

@Composable
fun BusinessItem(
    business: RestaurantEntity,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = business.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            error = painterResource(R.drawable.ic_launcher_foreground)
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
                text = "${Constants.restaurantStatusMessage} ${
                    if (!business.isOpen) Constants.closed else Constants.open
                }",
                fontSize = 14.sp
            )
        }
    }
}
