package HTML.Components.BaseComponents;

import HTML.Components.Component;

public class Header extends Component{
    private String text;
    private final int order;

    public Header(int order, String text){
        super(String.format("h%d", order));
        this.order = order;
        this.text = text;
        addChild(new RawText(text));
    }

    public String getText(){
        return text;
    }

    public int order(){
        return order;
    }
}
