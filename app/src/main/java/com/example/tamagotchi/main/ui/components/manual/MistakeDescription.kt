package com.example.tamagotchi.main.ui.components.manual

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamagotchi.R

@Composable
fun MistakeDescription() {
    Column(
        modifier = Modifier.padding(top = 4.dp, start = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(R.string.mistakes_into),
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.background
        )
        Text(
            text = stringResource(R.string.mistakes_possible),
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.background
        )

        stringResource(R.string.mistakes_list).split('\n').forEach { mistake ->
            BulletPointItem(text = mistake, textColor = MaterialTheme.colorScheme.background)
        }

        Text(
            text = stringResource(R.string.mistakes_redeem),
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.background
        )

        stringResource(R.string.mistakes_redeem_list).split('\n').forEach { redeem ->
            BulletPointItem(text = redeem, textColor = MaterialTheme.colorScheme.background)
        }

        Text(
            text = stringResource(R.string.mistakes_evolution),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.background,
            lineHeight = 16.sp
        )
    }
}