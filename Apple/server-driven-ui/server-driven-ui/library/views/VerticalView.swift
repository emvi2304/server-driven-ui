//
//  VerticalView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-20.
//

import SwiftUI

struct VerticalView: View {
    private var children: [AppComponentDefinition]
    
    init(children: [AppComponentDefinition]) {
        self.children = children
    }
    
    var body: some View {
        VStack{
            ForEach(children, id: \.id) { child in
                generateView(for: child)
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .topLeading) //https://stackoverflow.com/questions/56487323/make-a-vstack-fill-the-width-of-the-screen-in-swiftui
        .background(Color(.systemGroupedBackground))
    }
}

