package com.example.detectiveapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.detectiveapp.ui.theme.StatusAmber
import com.example.detectiveapp.ui.theme.StatusAmberBg
import com.example.detectiveapp.ui.theme.StatusGreen
import com.example.detectiveapp.ui.theme.StatusGreenBg

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val isClosed = status.trim().equals("Cerrado", ignoreCase = true)
    val contentColor = if (isClosed) StatusGreen else StatusAmber
    val bgColor = if (isClosed) StatusGreenBg else StatusAmberBg

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .border(1.dp, contentColor.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = status,
            color = contentColor,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}