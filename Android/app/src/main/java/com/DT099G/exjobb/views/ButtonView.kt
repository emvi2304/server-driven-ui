package com.DT099G.exjobb.views

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.String

@Composable
fun ButtonView(text: String, formAction:String? = null, action: () -> Unit){
    Button(modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 0.dp), onClick = {
        action()
    }) {
        Text(text = text, fontSize = 16.sp)
    }
}


@Preview
@Composable
fun CreateButtonView(){
    ButtonView(text = "Hello", formAction = "PrintHej", action = { print("rwer")})
}