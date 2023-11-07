package HTML.Components.BaseComponents;

import HTML.Components.Component;

public class Header extends Component{
    private String text;

    public Header(int type, String text){
        super(String.format("h%d", type));
        this.text = text;
        addChild(new RawText(text));
    }

    public String getText(){
        return text;
    }
}
