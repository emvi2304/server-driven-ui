//
//  LayoutViewModel.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-04-14.
//

import Foundation

@MainActor
@Observable
class LayoutViewModel {
    var viewLayout : AppComponentDefinition? = nil
    
    var currentPage: String = "/start"
    
    func navigation(to page: String) async {
        await fetchAPI(page: page)
    }
    
    func getWeather(access: String, lon: String, lat: String) async {
        await fetchWeather(access: access, lon: lon, lat: lat)
    }
    
    /**
     *  Hämtar layout från backend
     */
    private func fetchAPI(page: String) async{
        guard let url = URL(string: "http://localhost:3000" + page) else { return }
        
        do {
            let (data , _) = try await URLSession.shared.data(from: url)
            
            if let data = try? JSONDecoder().decode(AppComponentDefinition.self, from: data) {
                
                await MainActor.run {
                    self.viewLayout = data
                }
            } else {
                print("Failed to decode json")
            }
        } catch {
            print("Error: \(error)")
        }
    }
    
    
    /**
     *  Hämtar väderdata och layut fårn servern
     */
    func fetchWeather(access: String, lon: String, lat: String) async{
        
        // Parameterar
        let queryItems = [
            URLQueryItem(name: "access", value: access),
            URLQueryItem(name: "lon", value: lon),
            URLQueryItem(name: "lat", value: lat)
        ]
        // bas url
        var urlComp = URLComponents(string: "http://localhost:3000/weather/data")!
        // Lägg ihop bas url med parameterarna
        urlComp.queryItems = queryItems
        
        
        guard let url = urlComp.url else { return }
        
        do {
            let (data , _) = try await URLSession.shared.data(from: url)
            
            if let data = try? JSONDecoder().decode(AppComponentDefinition.self, from: data) {
                
                await MainActor.run {
                    self.viewLayout = data
                }
            } else {
                print("Failed to decode json")
                
            }
        } catch {
            print("Error: \(error)")
        }
    }
    
    /**
     *  Överför pengar från ett konto till ett annat.
     */
    //https://medium.com/@canakyildz/shifting-to-server-side-swift-node-js-ea94c0035998 2026-04-24
    func transferBalance(from: String, to: String, sum: Double) async -> ServerReponse{
        guard let url = URL(string: "http://localhost:3000/banken/transferBalance") else { return ServerReponse(title: "false", message: "Ingen kontakt med servern")}
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        let body: [String: Any] = ["from": from, "to": to, "sum": sum]
        
        request.httpBody = try? JSONSerialization.data(withJSONObject: body)
        
        // ta emot svar
        do {
            let (data , _) = try await URLSession.shared.data(for: request)
            
            let response:ServerReponse = try JSONDecoder().decode(ServerReponse.self, from: data)
            
            return response
        } catch {
            print("Error: \(error)")
            return ServerReponse(title: "Misslyckades", message: "Ett fel uppstod, överföringen misslyckades")
        }
    }
    
    /**
     *  Ta bort ett konto
     */
    func removeAccount(account: String) async -> ServerReponse{
        guard let url = URL(string: "http://localhost:3000/banken/Account/Remove") else { return ServerReponse(title: "false", message: "Ingen kontakt med servern")}
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
    
        request.httpBody = try? JSONSerialization.data(withJSONObject: ["account": account])
        
        // ta emot svar
        do {
            let (data , _) = try await URLSession.shared.data(for: request)
            
            let response:ServerReponse = try JSONDecoder().decode(ServerReponse.self, from: data)
            
            return response
        } catch {
            print("Error: \(error)")
            return ServerReponse(title: "Misslyckades", message: "Ett fel uppstod, gick ej att ta bort kontot.")
        }
    }
    
    /**
     *  Skapa ett konto
     */
    func addAccount(currency: String) async -> ServerReponse{
        guard let url = URL(string: "http://localhost:3000/banken/Account/Add") else { return ServerReponse(title: "false", message: "Ingen kontakt med servern")}
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
    
        request.httpBody = try? JSONSerialization.data(withJSONObject: ["currency": currency])
        
        // ta emot svar
        do {
            let (data , _) = try await URLSession.shared.data(for: request)
            
            let response:ServerReponse = try JSONDecoder().decode(ServerReponse.self, from: data)

            return response
        } catch {
            print("Error: \(error)")
            return ServerReponse(title: "Misslyckades", message: "Ett fel uppstod, gick ej att skapa kontot.")
        }
    }
}

struct ServerReponse: Codable {
    let title: String
    let message: String
}
