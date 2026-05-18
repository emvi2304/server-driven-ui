//
//  DropDownView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-21.
//

import SwiftUI

struct DropDownAccountView: View {
    private let text: String
    private let action: String?
    private let accountItems: [Account]
    @Binding var selected: String
    
    init(text: String, action: String, accountItems: [Account], selected: Binding<String>) {
        self.text = text
        self.action = action
        self.accountItems = accountItems
        self._selected = selected
    }
    
    
    // https://developer.apple.com/documentation/SwiftUI/Picker#Iterating-over-a-pickers-options
    var body: some View {
        HStack{
            Picker(text, selection: $selected){
                ForEach(accountItems, id: \.id){ item in
                    Text(item.accountNumber + " - " + String(item.accountBalance) + " " +  item.currency ).tag(item.accountNumber)
                }
            }.pickerStyle(.menu)
        }
        .padding(12)
    }
}

struct DropDownStringView: View {
    private let text: String
    private let action: String?
    private let stringItems: [String]
    @Binding var selected: String
    
    init(text: String, action: String, stringItems: [String], selected: Binding<String>) {
        self.text = text
        self.action = action
        self.stringItems = stringItems
        self._selected = selected
    }
    
    
    // https://developer.apple.com/documentation/SwiftUI/Picker#Iterating-over-a-pickers-options
    var body: some View {
        HStack{
            Picker(text, selection: $selected){
                ForEach(stringItems, id: \.self){ item in
                    Text(item).tag(item)
                }
            }.pickerStyle(.menu)
        }
        .padding(12)
    }
}
