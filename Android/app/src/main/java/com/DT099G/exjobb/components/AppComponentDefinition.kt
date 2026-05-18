package com.DT099G.exjobb.components

import kotlinx.serialization.Serializable

// --------- AppComponentDefinition --------
@Serializable
data class AppComponentDefinition(
    val name:Component,
    val props: ComponentProps? = null,
    val children: List<AppComponentDefinition>? = null,
    val key: String? = null
)


@Serializable
enum class Component{
    // Komponenter
    ListComponent,
    ListSectionComponent,
    TextComponent,
    HeaderComponent,
    ButtonComponent,
    NavigationBarComponent,
    AccountCardComponent,
    DropDownAccountComponent,
    DropDownStringComponent,
    InputComponent,
    FormComponent,
    LocationPermissionComponent,


    // Layouts
    HorizontalLayout,
    VerticalLayout,


    // Vyer
    NavigationView,
    BankView,
    NoDestinationView,
    TransferBalanceView,
    RemoveAccountView,
    AddAccountView,
    LocationPermissionView,
    WeatherView,
    NoAccessWeatherView
}

@Serializable
data class ComponentProps(
    var title: String? = null,
    var text: String? = null,
    var id: Int? = null,
    var action: String? = null,
    var formAction: String? = null,
    var pages: List<Page>? = null,
    var pageName: String? = null,
    var destination: String? = null,
    var accountNumber: String? = null,
    var accountBalance: Double? = null,
    var currency: String? = null,
    var accountItems: List<Account>? = null,
    var stringItems: List<String>? = null,
    var sectionTitle: String? = null

)
