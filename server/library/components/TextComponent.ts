import type { AppComponentDefinition } from "../types"

type props = {
  text: String
}

function TextComponent(text: String): AppComponentDefinition {
  return{
    name: 'TextComponent',
    props: {text: text}
  }
}

export { TextComponent }