//
//  ListSectionView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-20.
//

import SwiftUI

struct ListSectionView: View {
    private let title: String
    private let listItems: [AppComponentDefinition]

    
    init(title: String, listItems: [AppComponentDefinition]) {
        self.title = title
        self.listItems = listItems
    }
    
    var body: some View {
        VStack(alignment: .leading){
            Text(title).font(.title2)
            ForEach(listItems, id: \.id){ item in
                generateView(for: item)
            }
        }
        .padding(20)
    }
}
   
