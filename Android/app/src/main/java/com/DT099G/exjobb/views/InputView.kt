package com.DT099G.exjobb.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.String


//https://developer.android.com/develop/ui/compose/text/user-input?textfield=state-based
@Composable
fun InputView(title: String, text: String, selectedSum: MutableState<Double>){
    val sumState = rememberTextFieldState()

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)){
        Text(title)
        OutlinedTextField(
            state = sumState,
            lineLimits = TextFieldLineLimits.SingleLine,
            placeholder = { Text(text) },
            inputTransformation = InputTransformation.maxLength(6),
            modifier = Modifier.width(330.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
        )
        //https://developer.android.com/develop/ui/compose/text/migrate-state-based#conforming-approach
        //När sumstate.text ändras triggas LaunchedEffect och värdet tilldelas selectedSum
        LaunchedEffect(key1 = sumState.text) {
            selectedSum.value = sumState.text.toString().toDoubleOrNull() ?: 0.0
        }
    }
}
