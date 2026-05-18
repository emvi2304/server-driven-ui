//
//  FormView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-24.
//

import SwiftUI

struct FormView: View {
    private let formItems: [AppComponentDefinition]
    
    @Environment(LayoutViewModel.self)
    private var viewModel: LayoutViewModel
    @Environment(\.dismiss) var dismiss
    
    
    init( formItems: [AppComponentDefinition]) {
        self.formItems = formItems
    }
    
    @State public var fromAccount: String = ""
    @State public var toAccount: String = ""
    @State public var selected: String = ""
    @State public var sum: Double = 0
    @State public var displayAlert: Bool = false
    @State public var alert: ServerReponse? = nil
    
    
    
    var body: some View {
        Form{
            ForEach(formItems, id: \.id){ item in
                switch item.name {
                case .DropDownAccountComponent:
                    generateDropDownAccountView(for: item)
                case .DropDownStringComponent:
                    generateDropDownStringView(for: item)
                case .InputComponent:
                    InputView(title: item.props?.title ?? "Unknown value", text: item.props?.text ?? "Unknown value", sum: $sum)
                case .ButtonComponent:
                    generateButtonView(for: item)
                default:
                    Text(item.name.rawValue)
                }
            }
        }
        // https://developer.apple.com/documentation/swiftui/view/alert(_:ispresented:presenting:actions:message:)-8584l
        .alert(alert?.title ?? "Information", isPresented: $displayAlert, presenting: alert){ alert in
            Button("OK"){
                dismiss()
            }
        } message: { alert in
            Text(alert.message)
        }
    }
    
    private func generateDropDownAccountView(for item: AppComponentDefinition) -> some View{
        switch item.props?.action{
        case "FromAccount":
            DropDownAccountView(text: item.props?.text ?? "Unknown value", action: item.props?.action ?? "Unknown value", accountItems: item.props?.accountItems ?? [], selected: $fromAccount)
        case "ToAccount":
            DropDownAccountView(text: item.props?.text ?? "Unknown value", action: item.props?.action ?? "Unknown value", accountItems: item.props?.accountItems ?? [], selected: $toAccount)
        default:
            DropDownAccountView(text: item.props?.text ?? "Unknown value", action: item.props?.action ?? "Unknown value", accountItems: item.props?.accountItems ?? [], selected: $selected)
        }
    }
    
    
    private func generateDropDownStringView(for item: AppComponentDefinition) -> some View{
        DropDownStringView(text: item.props?.text ?? "Unknown value", action: item.props?.action ?? "Unknown value", stringItems: item.props?.stringItems ?? [], selected: $selected)
    }
    
    // Skapande av knapp med olika funktioner för formuläret
    private func generateButtonView(for item: AppComponentDefinition) -> some View{
        switch item.props?.formAction{
        case "Transfer":
            ButtonView(text: item.props?.text ?? "Unknown value", formAction: item.props?.formAction ?? "Unknown value", action:{
                Task{
                    let response:ServerReponse = await viewModel.transferBalance(from: fromAccount, to: toAccount, sum: sum)
                    alert = response
                    displayAlert = true
                }
            })
        case "Remove":
            ButtonView(text: item.props?.text ?? "Unknown value", formAction: item.props?.formAction ?? "Unknown value", action:{
                Task{
                    let response:ServerReponse = await viewModel.removeAccount(account: selected)
                    alert = response
                    displayAlert = true
                }
            })
            
        case "Add":
            ButtonView(text: item.props?.text ?? "Unknown value", formAction: item.props?.formAction ?? "Unknown value", action:{
                Task{
                    let response:ServerReponse = await viewModel.addAccount(currency: selected)
                    alert = response
                    displayAlert = true
                }
            })
        default:
            ButtonView(text: item.props?.text ?? "Unknown value", formAction: item.props?.formAction ?? "Unknown value", action:{})
        }
    }
}
