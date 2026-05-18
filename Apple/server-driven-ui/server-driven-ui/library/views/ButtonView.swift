//
//  ButtonView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-09.
//


import SwiftUI

//Implementation av en ButtonComponent
struct ButtonView: View {
    private let text: String
    private let formAction: String
    private let action: () -> Void
    
    
    @Environment(LayoutViewModel.self)
    private var viewModel: LayoutViewModel
    
    init(text: String, formAction: String = "", action: @escaping () -> Void = {}) {
        self.text = text
        self.formAction = formAction
        self.action = action
    }
    
    var body: some View {
        Button(text){
            action()
        }.buttonStyle(GrowingButtonStyle())
    }
}



struct GrowingButtonStyle: ButtonStyle {
  func makeBody(configuration: Configuration) -> some View {
    configuration.label
      .padding()
      .frame(maxWidth: .infinity)
      .background(Color(red: 0.0, green: 0.3, blue: 0.7))
      .foregroundStyle(.white)
      .clipShape(Capsule())
      .scaleEffect(configuration.isPressed ? 1.1 : 1)
      .animation(.easeOut(duration: 0.2), value: configuration.isPressed)
  }
}
