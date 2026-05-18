export type AppComponentDefinition = {
    name: String;
    props?: any;
    children?: (AppComponentDefinition)[]
    key?: String
}

export type ServerResponseMessage = {
  title: string
  message: string
}
