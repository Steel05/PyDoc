package HTML.Components.BaseComponents;

import HTML.Components.Component;

public class RawText extends Component{
    private String text;

    public RawText(String text){
        super("");
        this.text = text;
    }

    public String getText(){
        return text;
    }

    @Override
    public String write(){
        return text;
    }
}
