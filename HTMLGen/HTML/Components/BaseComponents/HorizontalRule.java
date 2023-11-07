package HTML.Components.BaseComponents;

import HTML.Components.Component;

public class HorizontalRule extends Component{
    public HorizontalRule(){
        super("hr");
    }

    @Override
    public String write(){
        return "<hr />";
    }
}
