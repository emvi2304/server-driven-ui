//
//  HorizontalView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-20.
//

import SwiftUI

struct HorizontalView: View {
    private var children: [AppComponentDefinition]
    
    init(children: [AppComponentDefinition]) {
        self.children = children
    }
    
    var body: some View {
        HStack{
            ForEach(children, id: \.id) { child in
                generateView(for: child)
            }
        }
        .frame(maxWidth: .infinity)
        .padding(5)
    }
}
