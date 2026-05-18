import type { AppComponentDefinition } from "../types"

export type Account = {
  id: number
  accountNumber: String
  accountBalance: number
  currency: String
}

function AccountCardComponent({id, accountNumber, accountBalance, currency}:Account): AppComponentDefinition {
  return{
    name: 'AccountCardComponent',
    props: {id: id, accountNumber: accountNumber, accountBalance: accountBalance, currency: currency}
  }
}

export { AccountCardComponent }
