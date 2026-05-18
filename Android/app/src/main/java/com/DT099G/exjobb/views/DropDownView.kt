package com.DT099G.exjobb.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.DT099G.exjobb.components.Account

// https://developer.android.com/develop/ui/compose/components/menu
@Composable
fun DropDownAccountView(text: String, action: String, accountItems: List<Account>, selected: MutableState<String>){
    var expanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)){
        Text(text)
        OutlinedButton(onClick = { expanded = !expanded },
            shape = RectangleShape,
            modifier = Modifier
                .width(330.dp)
                .height(50.dp))
        {
            Text(selected.value, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        }

        DropdownMenu( expanded = expanded,
            onDismissRequest = {expanded = false },
            modifier = Modifier
                .width(330.dp)
        ) {
            accountItems.forEach { option ->
                DropdownMenuItem(text = { Text(option.accountNumber + " - " +  option.accountBalance) }, onClick = {
                    selected.value = option.accountNumber
                    expanded = false
                })
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun DropDownStringView(text: String, action: String, stringItems: List<String>, selected: MutableState<String>){
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)){
        Text(text)
        OutlinedButton(onClick = { expanded = !expanded },
            shape = RectangleShape,
            modifier = Modifier
                .width(330.dp)
                .height(50.dp))
        {
            Text(selected.value, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        }

        DropdownMenu( expanded = expanded,
            onDismissRequest = {expanded = false },
            modifier = Modifier
                .width(330.dp)
        ) {
            stringItems.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    selected.value = option
                    expanded = false
                })
                HorizontalDivider()
            }
        }
    }
}

