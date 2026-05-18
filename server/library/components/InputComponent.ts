import type { AppComponentDefinition } from "../types"

type props = {
    title: String
    text: String
}

function InputComponent({title, text}:props): AppComponentDefinition {
  return{
    name: 'InputComponent',
    props: {title: title, text:text}
  }
}

export { InputComponent }

