package HTML.Components.BaseComponents;

import HTML.Components.Component;

public class Paragraph extends Component {
    private String text;
    
    public Paragraph(String text){
        super("p");
        this.text = text;
        addChild(new RawText(text));
    }

    public String getText(){
        return text;
    }
}
