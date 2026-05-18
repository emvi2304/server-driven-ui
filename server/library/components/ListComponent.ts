import type { AppComponentDefinition } from "../types"

type props = {
  title: String
}


function ListComponent({title}:props, listChildren: AppComponentDefinition[]): AppComponentDefinition {
  return{
    name: 'ListComponent',
    props: {title: title},
    children: listChildren,
  }
}

export { ListComponent }