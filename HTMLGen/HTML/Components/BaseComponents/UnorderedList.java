package HTML.Components.BaseComponents;

import HTML.Components.Component;

public class UnorderedList extends Component {
        public UnorderedList(){
        super("ul");
    }

    /**
     * This function will automatically wrap the child in a {@link ListItem} component 
     * if it is not already one itself.
     */
    @Override
    public Component addChild(Component child){
        Component comp = child instanceof ListItem ? child : new ListItem().addChild(child);
        super.addChild(comp);

        return this;
    }
}
