package com.example.digitalpet.main.ui.components.manual

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BulletPointItem(text: String, textColor: Color = MaterialTheme.colorScheme.primary, modifier: Modifier = Modifier){
    Row(
    modifier = modifier
    .fillMaxWidth()
    .padding(start = 4.dp),
    verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "• ",
            style = MaterialTheme.typography.bodySmall,
            color = textColor,
            lineHeight = 16.sp
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = textColor,
            lineHeight = 16.sp
        )
    }
}