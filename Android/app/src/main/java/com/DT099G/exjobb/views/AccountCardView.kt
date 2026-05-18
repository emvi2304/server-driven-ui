package com.DT099G.exjobb.views
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.String

@Composable
fun AccountCardView(id: Int, accountNumber: String, accountBalance:Double, currency: String){
    Card(modifier = Modifier
        .padding(0.dp,0.dp,0.dp,16.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Kontonummer: $accountNumber", fontSize = 16.sp, fontWeight = Bold)
            Text(text = "$accountBalance $currency", fontSize = 16.sp)
        }
    }
}


@Preview
@Composable
fun CreateAccountCardView(){
    AccountCardView(id = 1,accountNumber = "24345345", accountBalance = 123.8, currency = "sek")
}