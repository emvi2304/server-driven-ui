//
//  Modeller.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-10.
//

struct Account: Codable{
    let id: Int
    let accountNumber: String
    let accountBalance: Double
    let currency: String
    
}

struct Page: Codable, Identifiable{
    let pageName: String
    let destination: String
    
    var id: String { pageName }
}
