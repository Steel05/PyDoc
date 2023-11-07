package HTML.Components.BaseComponents;

import HTML.Components.Component;

public class Image extends Component {
    private final String source;
    private final String alt;

    public Image(String source, String alt){
        super("img");

        this.alt = alt;
        this.source = source;
    }

    public Image(String source){
        this(source, "");
    }

    @Override
    public String write(){
        if (alt.isBlank() || alt.isEmpty()){
            return String.format("<img src=\"%s\"%s>", source, constructCSSClassString());
        }

        return String.format("<img src=\"%s\"%s alt=\"%s\">", source, constructCSSClassString(), alt);
    }
}
