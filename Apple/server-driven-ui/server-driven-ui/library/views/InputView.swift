//
//  InputView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-22.
//
import SwiftUI

struct InputView: View {
    private let title: String
    private let text: String
    @Binding var sum: Double


    
    init(title: String, text: String, sum: Binding<Double>) {
        self.title = title
        self.text = text
        self._sum = sum
    }
    
    var body: some View {
        HStack(){
            Text(title)
            TextField(
                text,
                value: $sum,
                format: .number
            )
            .keyboardType(.decimalPad)
        }
        .padding(14)
    }
}
