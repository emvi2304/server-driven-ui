import type { AppComponentDefinition } from "../types"

type props = {
  title: String
}


function ListSectionComponent( {title}:props, children: AppComponentDefinition[]): AppComponentDefinition {
  return{
    name: 'ListSectionComponent',
    props: {title: title},
    children: children,
  }

}

export { ListSectionComponent }