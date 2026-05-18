package com.DT099G.exjobb

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.DT099G.exjobb.components.AppComponentDefinition
import com.DT099G.exjobb.components.Component
import com.DT099G.exjobb.views.AccountCardView
import com.DT099G.exjobb.views.ButtonView
import com.DT099G.exjobb.views.FormView
import com.DT099G.exjobb.views.HeaderView
import com.DT099G.exjobb.views.HorizontalView
import com.DT099G.exjobb.views.ListSectionView
import com.DT099G.exjobb.views.ListView
import com.DT099G.exjobb.views.NavigationBarView
import com.DT099G.exjobb.views.TextView
import com.DT099G.exjobb.views.VerticalView
import com.DT099G.exjobb.views.requestLocationPermission

/**
 * Funktion som genererar komponenterna på sidan utefter medskickad AppComponentDefinition komponent
 */
@Composable
fun ComponentView(component: AppComponentDefinition){
    when(component.name){
        Component.VerticalLayout -> VerticalView(
            children = component.children ?: listOf())
        Component.HorizontalLayout -> HorizontalView(
            children = component.children ?: listOf())
        Component.ButtonComponent -> ButtonView(
            text = component.props?.text ?: "Default",
            action = {})
        Component.HeaderComponent -> HeaderView(
            text = component.props?.text ?: "Default")
        Component.ListComponent -> ListView(
            title = component.props?.title ?: "Default",
            listItems = component.children ?: listOf())
        Component.TextComponent -> TextView(
            text = component.props?.text ?: "Default")
        Component.AccountCardComponent -> AccountCardView(
            id = component.props?.id ?: 0 ,
            accountNumber = component.props?.accountNumber ?: "Unknown",
            accountBalance = component.props?.accountBalance ?: 0.0,
            currency = component.props?.currency ?: "Unknown")
        Component.ListSectionComponent -> ListSectionView(
            title = component.props?.title ?: "Unknown",
            listItems = component.children ?: listOf())
        Component.NavigationBarComponent -> NavigationBarView(
            title = component.props?.title ?: "",
            sectionTitle = component.props?.sectionTitle ?: "",
            listItems = component.props?.pages ?: listOf())
        Component.LocationPermissionComponent -> requestLocationPermission()
        Component.FormComponent -> FormView(
            listItems = component.children ?: listOf())
        else -> println("Unknown type!")
    }

}


/**
 * Funktion som visar UI baserat på data från viewModel.
 * Hämtar startsida från API.
 */
@Composable
fun ContentView(viewModel: LayoutViewModel){
    val viewLayout = viewModel.viewLayout
    val view = LocalContext.current
    val act = view as Activity

    // Ändrar färg på statsu-bar ikonerna - https://developer.android.com/develop/ui/compose/system/insets-views-compose#system-bar-icons
    WindowCompat.getInsetsController(act.window, act.window.decorView)
        .isAppearanceLightStatusBars = true

    //Andorids inbyggda tillbaka knapp
    BackHandler {
        if (viewModel.currentPage == "/start") {
            act.finish()
        } else {
            viewModel.back()
        }
    }

    if (viewLayout != null){
        viewLayout.children?.forEach { child ->
            ComponentView(child)
        }
    } else{
        LoadingScreen()
    }

    // Unit = returnerar inget
    LaunchedEffect(Unit) {
        viewModel.load(viewModel.currentPage)
    }
}

@Composable
fun LoadingScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            Text("Kontaktar servern...")
        }
    }
}