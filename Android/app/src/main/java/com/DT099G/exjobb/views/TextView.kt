package com.DT099G.exjobb.views

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import kotlin.String

@Composable
fun TextView(text: String){
    Text(text)
}


@Preview
@Composable
fun CreateTextView(){
    TextView("Hello")
}