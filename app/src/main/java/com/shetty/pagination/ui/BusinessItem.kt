package com.shetty.pagination.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shetty.pagination.R
import com.shetty.pagination.models.Businesses
import com.shetty.pagination.utils.Constants

@Composable
fun BusinessItem(
    business: Businesses,
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
            Text(
                text = business.name ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            val address = business.location?.displayAddress?.joinToString(", ")
            if (!address.isNullOrEmpty()) {
                Text(
                    text = address,
                    fontSize = 16.sp
                )
            }
            
            Text(
                text = "${Constants.restaurantStatusMessage} ${
                    if (business.isClosed == true) Constants.closed else Constants.open
                }",
                fontSize = 14.sp
            )
        }
    }
}
