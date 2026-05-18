import { Low } from 'lowdb'
import { JSONFilePreset } from 'lowdb/node'
import type { Account } from '../library/components/AccountCardComponent'
import type { ServerResponseMessage } from '../library/types'

export let db: Low<Data>

type Data = {
    accounts: Account[]
    currencies: string[]
}

const defaultData: Data = { 
    accounts: [
        {
            "id": 1,
            "accountNumber": "123123",
            "accountBalance": 100.64,
            "currency": "sek"
        },
        {
            "id": 2,
            "accountNumber": "456456",
            "accountBalance": 3277.97,
            "currency": "sek"
        }
    ],
    currencies: [
        "sek",
        "euro"
    ]

}

export async function startDB(){
    db = await JSONFilePreset<Data>('./database/db.json', defaultData)
    await db.write()
}

export async function transferBalance(fromAccount: string, toAccount: string, amount: number): Promise<ServerResponseMessage> {
    if (!db) throw new Error (" Database not started ")


    // Avbryt om till och från konto är detsamma
    if(!fromAccount || !toAccount || !amount) return {title: "Misslyckades", message: "Minst ett fält är ej ifyllt"}

    // Avbryt om till och från konto är detsamma
    if(fromAccount == toAccount) return {title: "Misslyckades", message: "Till och Från innehåller samma konto"}
    

    const from = db.data.accounts.find(acc => acc.accountNumber === fromAccount)
    const to = db.data.accounts.find(acc => acc.accountNumber === toAccount)

    if (!from || !to ) return {title: "Misslyckades", message: "Hittar inte kontot/kontona"}

    if (amount <= 0) return {title: "Misslyckades", message: "Kan ej överföra 0 " + from.currency}

    // Avbryt om det inte finns tillräckligt med pengar för överföringen
    if (amount > from.accountBalance) return {title: "Misslyckades", message: "Otillräckligt med tillgångar på kontot"}

    from.accountBalance = Number((from.accountBalance - amount).toFixed(2))
    to.accountBalance = Number((to.accountBalance + amount).toFixed(2))

    await db.write()

    return {title: "Lyckades", message: "Överföring lyckades"}
}

/**
 * Lägga till ett nytt konto
 * 
 * @param currency valuta
 */
export async function addAccount(currency: string): Promise<ServerResponseMessage>{
    if (!db) throw new Error (" Database not started ")
    
    if ( !currency ) return { title: "Misslyckades", message: "Ingen valuta vald, konto kunde ej skapas" }

    //Skapar ett nytt konto
    const newAccount: Account = { 
        id: createId(),
        accountNumber: createAccountNumber(),
        accountBalance: 0.0,
        currency: currency
    }

    // pushar den till databasen
    db.data.accounts.push(newAccount)

    // Spara
    await db.write()
    return {title: "Lyckades", message: "Konto skapat"}
}

/**
 * Skapar unika id med autoicrement princip
 * @returns returnerar det högsta IDt + 1 för att skapa ett unikt id
 */
function createId(): number{
    if (db.data.accounts.length === 0 ) return 1 

    let highestId = 0

    for( const acc of db.data.accounts){
        if(acc.id > highestId){
            highestId = acc.id
        }
    }

    return highestId + 1
}


/**
 * Skapar ett random kontonummer mellan 999999 och 100000
 * @returns ett nummer mellan 999999 och 100000
 */
function createAccountNumber(): string {
    // max - min + 1 = 999999 - 100000 + 1 = 900 000
    const accountNumber = Math.floor(Math.random() * 900000) + 100000

    return accountNumber.toString()
}


/**
 * Tar bort ett konto från databasen
 * @param deleteAccountNumber Kontonummer att ta bort
 */
export async function deleteAccount(deleteAccountNumber: string): Promise<ServerResponseMessage>{
    if (!db) throw new Error (" Database not started ")

    // Kollar så kontot finns
    const account = db.data.accounts.find(acc => acc.accountNumber === deleteAccountNumber)
    if ( !account ) return {title: "Misslyckades", message: "Hittar inte kontot"}
    if ( account.accountBalance > 0 ) return {title: "Misslyckades", message: "Kontot ej tomt, se till att kontot är tomt först"}

    // Går igenom alla konton, behåller de som inte matcher deleteAccountId
    db.data.accounts = db.data.accounts.filter(
        acc => acc.accountNumber !== deleteAccountNumber
    )

    // Spara
    await db.write()
    return {title: "Lyckades", message: "Konto borttaget"}
}