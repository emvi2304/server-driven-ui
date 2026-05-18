import type { AppComponentDefinition } from "./types.ts"
import { TextComponent } from './components/TextComponent.ts'
import { HeaderComponent } from './components/HeaderComponent.ts'
import { ListComponent } from './components/ListComponent.ts'
import { ListSectionComponent } from './components/ListSectionComponent.ts'
import { ButtonComponent } from './components/ButtonComponent.ts'
import { NavigationBarComponent } from './components/NavigationBarComponent.ts'
import { AccountCardComponent } from './components/AccountCardComponent.ts'
import { VerticalLayout } from './components/VerticalLayout.ts'
import { HorizontalLayout } from './components/HorizontalLayout.ts'
import { db } from '../database/lowdb.ts'
import { DropDownAccountComponent, DropDownStringComponent } from './components/DropDownComponent.ts'
import { InputComponent } from './components/InputComponent.ts'
import { FormComponent } from './components/FormComponent.ts'
import { LocationPermissionComponent } from './components/LocationPermissionComponent.ts'
import type { wData } from "../weather_api/weather.ts"


export function NavigationView(): AppComponentDefinition {
  return{ 
    name:'NavigationView',
    children: [
      VerticalLayout([
        NavigationBarComponent( {title: "Startsida", pages: [{pageName: 'Banken', destination: '/banken'},
          {pageName: 'Plats - väder', destination: '/weather'}]
        })
      ])
    ]
  }
}

export function BankView(): AppComponentDefinition{
  return{
    name: "BankView",
    children: [
      VerticalLayout([
        HeaderComponent({text: "Banken"}),
        ListSectionComponent({title: "Dina bankkonton"}, 
          db.data.accounts.map(account => 
            AccountCardComponent({
              id: account.id,
              accountNumber: account.accountNumber,
              accountBalance: account.accountBalance,
              currency: account.currency
            })
          )
        ),
        NavigationBarComponent( {sectionTitle: "Hantering", pages: [{pageName: 'Överför pengar', destination: '/banken/transferBalance'},
          {pageName: 'Skapa bankkonto', destination: '/banken/Account/Add'}, {pageName: 'Ta bort bankkonto', destination: '/banken/Account/Remove'}]
        })

      ])
    ]
  }
}


export function TransferBalanceView(): AppComponentDefinition{
  return{
    name: "TransferBalanceView",
    children: [
      VerticalLayout([
        HeaderComponent({text: "Överför pengar"}),
        FormComponent([
          DropDownAccountComponent({text: "Från", action: "FromAccount"}, db.data.accounts ),
          DropDownAccountComponent({text: "Till", action: "ToAccount"}, db.data.accounts ),
          InputComponent({title: "Summa ", text: "Ange summa "}),
          ButtonComponent({text: 'Överför pengar', formAction: "Transfer", action: "transferBalance"}
        )]
        )
      ]),
    ]
  }
}

export function AddAccountView(): AppComponentDefinition{
  return{
    name: "AddAccountView",
    children: [
      VerticalLayout([
        HeaderComponent({text: "Skapa ett nytt bankkonto"}),
        FormComponent([
          DropDownStringComponent({text: "Val av valuta"}, db.data.currencies ),
          ButtonComponent({text: 'Skapa bankkonto', formAction: "Add"}
        )]
        )
      ]),
    ]
  }
}

export function RemoveAccountView(): AppComponentDefinition{
  return{
    name: "RemoveAccountView",
    children: [
      VerticalLayout([
        HeaderComponent({text: "Ta bort ett bankkonto"}),
        FormComponent([
          DropDownAccountComponent({text: "Välj bankkonto: "}, db.data.accounts ),
          ButtonComponent({text: 'Ta bort bankkonto', formAction: "Remove"})
        ])
      ]),
    ]
  }
}



export function NoDestinationView(): AppComponentDefinition{
  return{
    name: "NoDestinationView",
    children: [
      VerticalLayout([
        HeaderComponent({text: "Error: något gick fel"})
      ]),
    ]
  }
}


export function LocationPermissionView(): AppComponentDefinition{
  return{
    name: "LocationPermissionView",
    children: [
      VerticalLayout([
        LocationPermissionComponent()
      ]),
    ]
  }
}

export function WeatherView(weather: wData): AppComponentDefinition{
  return{
    name: "WeatherView",
    children: [
      VerticalLayout([
        HeaderComponent({text: "Vädret"}),
        TextComponent( "Temperaturen där du befinner dig"),
        TextComponent( "Lon: " + weather.lon),  
        TextComponent( "Lat: " + weather.lat),  
        TextComponent( ""),  
        HorizontalLayout([
          TextComponent( "Temperaturen är nu: "), 
          TextComponent( weather.temperaturNow + "°C" ), 
        ]),
        HorizontalLayout([
          TextComponent( "Temperaturen är snart: "), 
          TextComponent( weather.temperaturNextHour + "°C" ), 
        ])
      ]),
    ]
  }
} 


export function NoAccessWeatherView(): AppComponentDefinition{
  return{
    name: "NoAccessWeatherView",
    children: [
      VerticalLayout([
        HeaderComponent({text: "Vädret"}),
        TextComponent( "Åtkomst till platsdata krävs för att kunna visa vädret") 
      ]),
    ]
  }
}
