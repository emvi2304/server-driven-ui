//
//  server_driven_uiApp.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-09.
//

import SwiftUI

@main
struct server_driven_uiApp: App {
    @State private var viewModel = LayoutViewModel()
    
    var body: some Scene {
        WindowGroup {
            NavigationStack{
                ContentView()
            }
            .environment(viewModel)
        }
    }
}
