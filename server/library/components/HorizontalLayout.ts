import type { AppComponentDefinition } from "../types"


function HorizontalLayout(children: AppComponentDefinition[]): AppComponentDefinition {
  return{
    name: 'HorizontalLayout',
    children: children
  }
}

export { HorizontalLayout }