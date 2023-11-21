package HTML.Components.BaseComponents;

import HTML.Components.Component;

/**
 * A component which represents text in a paragraph.
 */
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
