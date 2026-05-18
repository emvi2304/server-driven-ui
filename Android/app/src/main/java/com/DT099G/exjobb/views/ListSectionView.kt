package com.DT099G.exjobb.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.DT099G.exjobb.components.AppComponentDefinition
import com.DT099G.exjobb.components.Component
import kotlin.String

@Composable
fun ListSectionView(title: String, listItems: List<AppComponentDefinition>){
    LazyColumn (modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 0.dp) ){
        stickyHeader {
            Text(text = title,fontSize = 18.sp, modifier = Modifier.padding(start = 2.dp, bottom = 6.dp))
        }
        items(listItems) { item ->
            when(item.name){
                Component.AccountCardComponent -> AccountCardView(id = item.props?.id ?: 0, accountNumber = item.props?.accountNumber ?: "Unknown", accountBalance = item.props?.accountBalance ?: 0.0, currency = item.props?.currency ?: "Unknown" )
                else -> Text("Okänt objekt")
            }
        }
    }
}

@Preview
@Composable
fun ViewListSectionView(){
    ListSectionView("Header", listOf())
}