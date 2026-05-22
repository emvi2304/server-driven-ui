//
//  ContentView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-09.
//

import SwiftUI

@ViewBuilder
func generateView(for component: AppComponentDefinition) -> some View {
    
    switch component.name {
    case .VerticalLayout:
        VerticalView(children: component.children ?? [])
    case .HorizontalLayout:
        HorizontalView(children: component.children ?? [])
    case .TextComponent:
        TextView(text: component.props?.text ?? "Value is missing")
    case .ButtonComponent:
        ButtonView(text: component.props?.text ?? "Value is missing")
    case .HeaderComponent:
        HeaderView(text: component.props?.text ?? "Value is missing")
    case .ListComponent:
        ListView(title: component.props?.title ?? "Value is missing", listItems: component.children ?? [])
    case .NavigationBarComponent:
        NavigationBarView(title: component.props?.title, sectionTitle: component.props?.sectionTitle, pages: component.props?.pages ?? [])
    case .AccountCardComponent:
        AccountCardView(accountNumber: component.props?.accountNumber ?? "Value is missing", accountBalance: component.props?.accountBalance ?? 0.0, currency: component.props?.currency ?? "Value is missing")
    case .ListSectionComponent:
        ListSectionView(title: component.props?.title ?? "Value is missing", listItems: component.children ?? [])
    case .FormComponent:
        FormView(formItems: component.children ?? [])
    case .LocationPermissionComponent:
        LocationPermissionView()
    default:
        Text(component.name.rawValue)
    }
}


struct ContentView: View {
    // Egen LayoutViewModel per view, så navigation stack ska fungera
    @State private var viewModel = LayoutViewModel()
    
    var page: String = "/start"

    var body: some View {
        Group{
            // Safe unwrap, kollar om viewModel.viewLayout har ett värde
            if let viewLayout = viewModel.viewLayout {
                if let first = viewLayout.children?.first {
                    generateView(for: first)
                }
            } else {
                ProgressView("Ett fel uppstod")
            }
        }
        .task(id: page) {
            await viewModel.navigation(to: page)
        }
        .refreshable {
            await viewModel.navigation(to: page)
        }
        .background(Color(.systemGroupedBackground))
    }
}

#Preview {
    @Previewable @State var viewModel = LayoutViewModel()
    ContentView()
        .environment(viewModel)
}


