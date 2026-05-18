import type { AppComponentDefinition } from "../types"


type props = {
  title?: String
  sectionTitle?: String
  pages: page[]
  
}

type page = {
  pageName: String
  destination: String
}


function NavigationBarComponent({title, sectionTitle, pages}:props, ): AppComponentDefinition {
  return{
    name: 'NavigationBarComponent',
    props: { title: title, sectionTitle: sectionTitle, pages: pages}
  }
}

export { NavigationBarComponent }