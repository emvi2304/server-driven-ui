package com.DT099G.exjobb.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.DT099G.exjobb.components.AppComponentDefinition
import kotlin.String

@Composable
fun ListView(title: String, listItems: List<AppComponentDefinition>){
    LazyColumn (modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 0.dp) ){
        stickyHeader {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
        items(listItems) { item ->
            TextView(item.props?.text ?: "Default text")

        }
    }
}

@Preview
@Composable
fun ViewListView(){
    ListView("Header", listOf())
}