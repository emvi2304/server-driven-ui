package com.DT099G.exjobb.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.DT099G.exjobb.components.Page
import kotlin.String
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.DT099G.exjobb.LayoutViewModel

@Composable
fun NavigationBarView(title: String?, sectionTitle: String?, listItems: List<Page>){
    val viewModel: LayoutViewModel = viewModel()

    LazyColumn ( modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 0.dp ) ){
        stickyHeader {
            if (!title.isNullOrEmpty()) {
                Text(
                    text = title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    fontWeight = Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            } else {
                Text(
                    text = sectionTitle ?: "",
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 16.sp,
                    fontWeight = Bold,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
        items( listItems ) { item ->
            Card(
                onClick = {
                    viewModel.load( page = item.destination )
                    //onNavigate(item.destination)
                },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp, vertical = 6.dp)

            ) {
                Row(
                    modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.pageName,
                        modifier = Modifier.padding( all = 16.dp ),
                        fontSize = 16.sp,
                        color = Color.Black,
                    )
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Navigate",
                        modifier = Modifier .padding( all = 16.dp )

                    )
                }
            }
        }
    }
}
