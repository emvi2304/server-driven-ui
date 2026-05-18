import type { AppComponentDefinition } from "../types"


function FormComponent( children: AppComponentDefinition[]): AppComponentDefinition {
  return{
    name: 'FormComponent',
    children: children,
  }

}

export { FormComponent }