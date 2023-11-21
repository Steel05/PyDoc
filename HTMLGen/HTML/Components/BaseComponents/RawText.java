package HTML.Components.BaseComponents;

import HTML.Components.Component;

/**
 * A component which represents raw text which is not directly surrounded by a tag.
 */
public class RawText extends Component{
    private String text;

    public RawText(String text){
        super("");
        this.text = text;
    }

    /**
     * Retrieves the text contained within this component
     * @return The text this component contains
     */
    public String getText(){
        return text;
    }

    @Override
    public String write(){
        return text;
    }
}
