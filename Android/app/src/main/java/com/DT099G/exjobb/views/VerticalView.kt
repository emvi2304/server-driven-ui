package com.DT099G.exjobb.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.DT099G.exjobb.ComponentView
import com.DT099G.exjobb.components.AppComponentDefinition


@Composable
fun VerticalView(children: List<AppComponentDefinition>){
    Column(modifier = Modifier.padding(24.dp) ) {
        children.forEach { child ->
            ComponentView(child)

        }
    }
}
