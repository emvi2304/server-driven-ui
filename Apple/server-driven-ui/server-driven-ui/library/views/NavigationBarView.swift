//
//  NavigationBarView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-09.
//

import SwiftUI

struct NavigationBarView: View {
    @Environment(LayoutViewModel.self)
    private var viewModel: LayoutViewModel
    
    private let title: String?
    private let sectionTitle: String?
    private let pages: [Page]
    
    init(title: String? = nil, sectionTitle: String? = nil, pages: [Page]) {
        self.title = title
        self.sectionTitle = sectionTitle
        self.pages = pages
    }
     
    
    // https://developer.apple.com/documentation/SwiftUI/NavigationLink#Link-to-a-destination-view
    var body: some View {
        NavigationStack {
            List{
                Section(header: Text(sectionTitle ?? "")){
                    ForEach(pages){page in
                        NavigationLink(page.pageName){
                            ContentView(page: page.destination)
                        }
                    }
                }
            }.navigationTitle(title ?? "")
        }
    }
 
}
