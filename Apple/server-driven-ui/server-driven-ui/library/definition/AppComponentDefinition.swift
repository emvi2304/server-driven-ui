//
//  AppComponentDefinition.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-10.
//

import Foundation


struct AppComponentDefinition: Codable, Identifiable{
    let name: Components
    let props: ComponentProps?
    let children: [AppComponentDefinition]?
    var key: String = UUID().uuidString
    
    var id: String { key }
    
    //Key genereras lokalt
    private enum CodingKeys: String, CodingKey {
        case name, props, children
    }
}


// Alla typer av komponeter som en AppComponentdefinition kan vara
enum Components: String, Codable{
    // Komponenter
    case ListComponent
    case ListSectionComponent
    case TextComponent
    case HeaderComponent
    case ButtonComponent
    case NavigationBarComponent
    case AccountCardComponent
    case DropDownAccountComponent
    case DropDownStringComponent
    case InputComponent
    case FormComponent
    case LocationPermissionComponent
    
    // Layouts
    case HorizontalLayout
    case VerticalLayout
    
    //Vyer
    case NavigationView
    case BankView
    case TransferBalanceView
    case NoDestinationView
    case RemoveAccountView
    case AddAccountView
    case LocationPermissionView
    case WeatherView
}

// Alla komponenters props (egenskaper)
struct ComponentProps: Codable{
    let title: String?
    let text: String?
    let id: Int?
    let action: String?
    let formAction: String?
    let pages: [Page]?
    let pageName: String?
    let destination: String?
    let accountNumber: String?
    let accountBalance: Double?
    let currency: String?
    let accountItems: [Account]?
    let stringItems: [String]?
    let sectionTitle: String?
}
