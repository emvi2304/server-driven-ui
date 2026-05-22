//
//  LocationPermissionView.swift
//  server-driven-ui
//
//  Created by Emma Vikström on 2026-05-07.
//

import SwiftUI
import CoreLocation
import Combine

//https://coledennis.medium.com/tutorial-connecting-core-location-to-a-swiftui-app-dc62563bd1de

struct LocationPermissionView: View {
    @StateObject var locationManager = LocationManager()
    @Environment(LayoutViewModel.self)
    private var viewModel: LayoutViewModel
    
    var body: some View {
        VStack {
            switch locationManager.authorizationStatus {
            case .authorizedWhenInUse:  // Location services are available.
                if let viewLayout = viewModel.viewLayout {
                    if let first = viewLayout.children?.first {
                        generateView(for: first)
                    }
                } else {
                    VStack{
                        ProgressView("Väntar på data...")
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                }
            case .restricted, .denied:  // Location services currently unavailable.
                if let viewLayout = viewModel.viewLayout {
                    if let first = viewLayout.children?.first {
                        generateView(for: first)
                    }
                } else {
                    VStack{
                        ProgressView("Väntar på data...")
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                }
            case .notDetermined:        // Authorization not determined yet.
                Text("Laddar...")
                ProgressView()
            default:
                VStack{
                    ProgressView()
                }
            }
        }
        .onChange(of: locationManager.authorizationStatus){
            Task{
                if(locationManager.authorizationStatus == .authorizedWhenInUse){
                    await viewModel.getWeather(access: "true", lon: locationManager.location?.coordinate.longitude.description ?? "Unknown", lat: locationManager.location?.coordinate.latitude.description ?? "Unknown")
                } else {
                    await viewModel.getWeather(access: "false", lon: "Unknown", lat: "Unknown")
                }
            }
        }
    }
}


class LocationManager: NSObject, ObservableObject, CLLocationManagerDelegate {
    private let locationManager = CLLocationManager()
    @Published var location: CLLocation?
    @Published var authorizationStatus: CLAuthorizationStatus?
    
    
    override init() {
        super.init()
        locationManager.delegate = self
    }
    
    func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
        switch manager.authorizationStatus {
            case .authorizedWhenInUse:  // Location services are available.
                authorizationStatus = .authorizedWhenInUse
                locationManager.requestLocation()
                break
            case .restricted:  // Location services currently unavailable.
                authorizationStatus = .restricted
                break
                
            case .denied:  // Location services currently unavailable.
                authorizationStatus = .denied
                break
                
            case .notDetermined:        // Authorization not determined yet.
                authorizationStatus = .notDetermined
                locationManager.requestWhenInUseAuthorization()
                break
                
            default:
                break
            }
    }
    
    // Berättar att ny platsdata finns
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        location = locations.first
    }
    
    // Berättar att location managern inte kunde hämta ett värde för platsdata
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("error: \(error.localizedDescription)")
    }
    
}

