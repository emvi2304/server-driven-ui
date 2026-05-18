package com.DT099G.exjobb.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.DT099G.exjobb.LayoutViewModel
import com.DT099G.exjobb.components.AppComponentDefinition
import com.DT099G.exjobb.components.Component

@Composable
fun FormView( listItems: List<AppComponentDefinition> ){

    val viewModel: LayoutViewModel = viewModel()

    var selectedTo = remember { mutableStateOf( value = "") }
    var selectedFrom = remember { mutableStateOf( value = "") }
    var selected = remember { mutableStateOf( value = "") }
    var transferSum = remember { mutableDoubleStateOf( value = 0.0 ) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn (modifier = Modifier
        .padding(0.dp, 24.dp, 0.dp, 0.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        items(listItems) { item ->
            when(item.name){
                Component.DropDownAccountComponent -> //generateDropDownAccountView(item = item) //DropDownAccountView(text = item.props?.text ?: "Unknown", accountItems = item.props?.accountItems ?: listOf())
                    when(item.props?.action){
                        "FromAccount" -> DropDownAccountView(text = item.props.text ?: "Unknown", action = item.props.action ?: "Unknown", accountItems = item.props.accountItems ?: listOf(), selected = selectedFrom)
                        "ToAccount" -> DropDownAccountView(text = item.props.text ?: "Unknown", action = item.props.action ?: "Unknown", accountItems = item.props.accountItems ?: listOf(), selected = selectedTo)
                        else -> DropDownAccountView(text = item.props?.text ?: "Unknown", action = item.props?.action ?: "Unknown", accountItems = item.props?.accountItems ?: listOf(), selected = selected)
                    }
                Component.DropDownStringComponent -> DropDownStringView(text = item.props?.text ?: "Unknown", action = item.props?.action ?: "Unknown", stringItems = item.props?.stringItems ?: listOf(), selected = selected)
                Component.InputComponent -> InputView(title = item.props?.title ?: "Unknown value", text = item.props?.text ?: "Unknown value", selectedSum = transferSum)
                Component.ButtonComponent ->
                    when(item.props?.formAction){
                        "Transfer" -> ButtonView(text = item.props.text ?: "Unknown value", formAction = item.props.formAction, action = {
                            viewModel.transfer(from = selectedFrom.value,  to = selectedTo.value, sum = transferSum.doubleValue)
                            showDialog = true
                        })
                        "Remove" -> ButtonView(text = item.props.text ?: "Unknown value", formAction = item.props.formAction, action = {
                            viewModel.remove(account = selected.value)
                            showDialog = true
                        })
                        "Add" -> ButtonView(text = item.props.text ?: "Unknown value", formAction = item.props.formAction, action = {
                            viewModel.add(currency = selected.value)
                            showDialog = true
                        })
                        else -> ButtonView(text = item.props?.text ?: "Unknown value", formAction = item.props?.formAction, action = {
                        })
                    }
                else -> Text("Okänt objekt")
            }
        }
    }

    // https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-alert-dialog.html
    if (showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        viewModel.serverResponse.value?.title = ""
                        viewModel.serverResponse.value?.message = ""
                        viewModel.back()
                    }
                ) {
                    Text("OK")
                }
            },
            title = {
                val response = viewModel.serverResponse.value

                if(!response?.title.isNullOrEmpty()){
                    Text(response.title ?: "Unknown")
                }},
            text = {
                val response = viewModel.serverResponse.value

                if(response?.message.isNullOrEmpty()){
                    CircularProgressIndicator()
                } else {
                    Text(response.message ?: "Unknown")
                }
            }
        )
    }
}