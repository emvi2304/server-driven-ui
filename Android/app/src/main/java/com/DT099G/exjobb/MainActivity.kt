package com.DT099G.exjobb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //Hämtar befintilig viewModel för denna skärm eler skapar en ny
            val viewModel: LayoutViewModel = viewModel()
            ContentView(viewModel)
        }



    }
}