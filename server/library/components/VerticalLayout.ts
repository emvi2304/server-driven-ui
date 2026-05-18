import type { AppComponentDefinition } from "../types"


function VerticalLayout(children: AppComponentDefinition[]): AppComponentDefinition {
  return{
    name: 'VerticalLayout',
    children: children
  }
}

export { VerticalLayout }