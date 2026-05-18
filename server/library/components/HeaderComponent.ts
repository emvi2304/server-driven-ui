import type { AppComponentDefinition } from "../types"

type props = {
    text: String
}

function HeaderComponent({text}:props): AppComponentDefinition {
  return{
    name: 'HeaderComponent',
    props: {text: text}
  }
}

export { HeaderComponent }