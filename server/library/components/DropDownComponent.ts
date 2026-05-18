import type { AppComponentDefinition } from "../types"
import type { Account } from "./AccountCardComponent"

type props = {
  text: String
  action?: String
}

function DropDownAccountComponent({text, action}:props, items: Account[]): AppComponentDefinition {
  return{
    name: 'DropDownAccountComponent',
    props: {text: text, action: action, accountItems: items}
  }
}


function DropDownStringComponent({text, action}:props, items: string[]): AppComponentDefinition {
  return{
    name: 'DropDownStringComponent',
    props: {text: text, action: action, stringItems: items}
  }
}


export { DropDownAccountComponent, DropDownStringComponent }

