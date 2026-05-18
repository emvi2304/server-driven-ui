import type { AppComponentDefinition } from "../types"

type props = {
  text: String
  formAction?: String
  action?: String
}

function ButtonComponent({text, formAction, action}:props): AppComponentDefinition {
  return{
    name: 'ButtonComponent',
    props: {text:text,  formAction: formAction, action: action}
  }
}

export { ButtonComponent }


/*

type props = {
  text: String
  action: String
  destination?: String
}

function ButtonComponent({text, action, destination}:props): AppComponentDefinition {
  return{
    name: 'ButtonComponent',
    props: {text:text,  action: action, destination: destination}
    //children: action
  }
}

*/

