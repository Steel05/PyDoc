package HTML;

import HTML.Components.BaseComponents.Header;
import HTML.Components.BaseComponents.HorizontalRule;
import HTML.Components.BaseComponents.Image;
import HTML.Components.BaseComponents.Paragraph;
import HTML.Components.BaseComponents.RawText;

/**
 * Factory class to reduce imports
 */
public class Tags {
    /**
     * Produces a new HorizontalRule component
     * <p>{@code <hr />}
     * @return A horizontal rule component
     */
    public static HorizontalRule hr(){
        return new HorizontalRule();
    }
    /**
     * Produces a RawText component
     * @param text The text to place
     * @return The raw text component
     */
    public static RawText rawText(String text){
        return new RawText(text);
    }
    public static Header h1(String text){
        return new Header(1, text);
    } 
    public static Header h2(String text){
        return new Header(2, text);
    }
    public static Header h3(String text){
        return new Header(3, text);
    }
    public static Paragraph p(String text){
        return new Paragraph(text);
    }
    public static Image img(String source){
        return new Image(source);
    }
    public static Image img(String source, String alt){
        return new Image(source, alt);
    }
}
