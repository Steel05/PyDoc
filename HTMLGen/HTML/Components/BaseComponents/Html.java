package HTML.Components.BaseComponents;

import HTML.Components.Component;

public class Html extends Component {

    public Html() {
        super("html");
    }

    @Override
    public boolean hasParent(){
        return true;
    }
}
