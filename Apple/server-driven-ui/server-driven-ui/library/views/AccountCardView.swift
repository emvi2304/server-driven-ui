//
//  AccountCardView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-20.
//

import SwiftUI

struct AccountCardView: View {
    private let accountNumber: String
    private let accountBalance: Double
    private let currency: String

    
    init(accountNumber: String, accountBalance: Double, currency: String) {
        self.accountNumber = accountNumber
        self.accountBalance = accountBalance
        self.currency = currency
    }
    
    var body: some View {
        HStack(alignment: .top) {
            Text("Kontonr: " + accountNumber)
                .fontWeight(.bold)
                .foregroundColor(.primary)
            Spacer()
            Text("\(accountBalance, specifier: "%.2f") \(currency)")
                .foregroundColor(.primary)
        }
        .padding(22)
        .background(Color.white)
        .cornerRadius(12)
    }
}

struct AccountCardView_previews: PreviewProvider {
    @available(iOS 13.0, macOS 10.15, tvOS 13.0, watchOS 6.0, *)
    static var previews: some View {
        AccountCardView(accountNumber: "24345345", accountBalance: 123.8, currency: "sek")
    }
}
