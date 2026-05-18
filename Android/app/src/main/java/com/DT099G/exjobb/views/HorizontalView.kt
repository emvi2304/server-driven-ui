package com.DT099G.exjobb.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.DT099G.exjobb.ComponentView
import com.DT099G.exjobb.components.AppComponentDefinition


@Composable
fun HorizontalView(children: List<AppComponentDefinition>){
    Row(modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start) {
        children.forEach { child ->
            ComponentView(child)

        }
    }
}
