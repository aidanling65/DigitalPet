package com.example.digitalpet.main.ui.components.manual.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalpet.R
import com.example.digitalpet.main.ui.components.manual.BulletPointItem

@Composable
fun MistakeContent() {
    Column(
        modifier = Modifier
            .padding(top = 4.dp, start = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(R.string.mistakes_into),
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 16.sp,
            color = Color.Black
        )
        Text(
            text = stringResource(R.string.mistakes_possible),
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 16.sp,
            color = Color.Black
        )

        stringResource(R.string.mistakes_list).split('\n').forEach { mistake ->
            BulletPointItem(text = mistake, textColor = Color.Black)
        }

        Text(
            text = stringResource(R.string.mistakes_redeem),
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 16.sp,
            color = Color.Black
        )

        stringResource(R.string.mistakes_redeem_list).split('\n').forEach { redeem ->
            BulletPointItem(text = redeem, textColor = Color.Black)
        }

        Text(
            text = stringResource(R.string.mistakes_evolution),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Black,
            lineHeight = 16.sp
        )
    }
}