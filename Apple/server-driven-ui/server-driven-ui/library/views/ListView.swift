//
//  ListView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-09.
//

import SwiftUI

struct ListView: View {
    private let title: String
    private let listItems: [AppComponentDefinition]

    
    init(title: String, listItems: [AppComponentDefinition]) {
        self.title = title
        self.listItems = listItems
    }
    
    var body: some View {
        List{
            Section(header: Text(title)){
                ForEach(listItems, id: \.id){ item in
                    TextView(text: item.props?.text ?? "Error, no text")
                }
            }
        }
    }
}
    
