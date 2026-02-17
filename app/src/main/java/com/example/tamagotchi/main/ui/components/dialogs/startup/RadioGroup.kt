package com.example.tamagotchi.main.ui.components.dialogs.startup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.theme.AppTheme
import com.example.tamagotchi.theme.TamagotchiTheme

data class RadioButtonItem(
    val id: Int,
    val title: String,
    val color: Color,
)

@Composable
fun RadioGroupItem(
    item: RadioButtonItem,
    selected: Boolean,
    onClick: ((Int) -> Unit)?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = { onClick?.invoke(item.id) },
                role = Role.RadioButton
            )
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(86.dp)
                .shadow(8.dp, RoundedCornerShape(25))
                .clip(RoundedCornerShape(25))
                .background(item.color)
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(25))
        )
        Spacer(Modifier.height(8.dp))
        RadioButton(
            selected = selected,
            onClick = null,
        )
    }
}

@Composable
fun RadioGroup(
    items: Iterable<RadioButtonItem>,
    selected: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier
            .selectableGroup()
            .height(256.dp)
            .then(modifier)
    ) {
        items(items.toList()){item->
            RadioGroupItem(
                item = item,
                selected = item.id == selected,
                onClick = onClick
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun RadioGroupPreview() {
    val themeItems = AppTheme.entries.map { theme ->  RadioButtonItem(theme.ordinal, theme.name,
        theme.theme.background) }
    TamagotchiTheme(AppTheme.GREEN) {
        RadioGroup(themeItems, AppTheme.GREEN.ordinal, {})
    }
}