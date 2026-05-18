//
//  HeaderView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-09.
//

import SwiftUI

struct HeaderView: View {
    private let text: String
    
    init(text: String) {
        self.text = text
    }
    
    var body: some View {
        Text(text).font(.title)
    }
}

struct HeaderView_previews: PreviewProvider {
    @available(iOS 13.0, macOS 10.15, tvOS 13.0, watchOS 6.0, *)
    static var previews: some View {
        HeaderView(text: "Titel")
    }
}
