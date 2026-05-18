package com.DT099G.exjobb.views

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.String

@Composable
fun HeaderView(text: String){
    Text(text, fontSize = 24.sp, fontWeight = Bold, modifier = Modifier.padding(top = 20.dp))
}

@Preview
@Composable
fun ViewHeaderView(){
    HeaderView("Hello")
}