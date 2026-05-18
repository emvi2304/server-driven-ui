import type { AppComponentDefinition } from "../types"

type props = {
    text: String
}

function LocationPermissionComponent(): AppComponentDefinition {
  return{
    name: 'LocationPermissionComponent',
  }
}

export { LocationPermissionComponent }