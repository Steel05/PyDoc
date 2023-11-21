package HTML;

import HTML.Components.BaseComponents.Division;
import HTML.Components.BaseComponents.Header;
import HTML.Components.BaseComponents.HorizontalRule;
import HTML.Components.BaseComponents.Image;
import HTML.Components.BaseComponents.OrderedList;
import HTML.Components.BaseComponents.Paragraph;
import HTML.Components.BaseComponents.RawText;
import HTML.Components.BaseComponents.UnorderedList;

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
    /**
     * Produces a Header component of order {@code 1}
     * @param text The text contained within this header
     * @return the header component
     */
    public static Header h1(String text){
        return new Header(1, text);
    } 
    /**
     * Produces a Header component of order {@code 2}
     * @param text The text contained within this header
     * @return the header component
     */
    public static Header h2(String text){
        return new Header(2, text);
    }
    /**
     * Produces a Header component of order {@code 3}
     * @param text The text contained within this header
     * @return the header component
     */
    public static Header h3(String text){
        return new Header(3, text);
    }
    /**
     * Produces a Paragraph component
     * @param text The text contained within this paragraph
     * @return The paragraph component
     */
    public static Paragraph p(String text){
        return new Paragraph(text);
    }
    /**
     * Produces an Image component
     * @param source The source link for the image
     * @return The image component
     */
    public static Image img(String source){
        return new Image(source);
    }
    /**
     * Produces an Image component
     * @param source The source link for the image
     * @param alt The alternative view for the image should the image be unviewable
     * @return The image component
     */
    public static Image img(String source, String alt){
        return new Image(source, alt);
    }
    /**
     * Produces a Division component.
     * @return The division component
     */
    public static Division div(){
        return new Division();
    }
    /**
     * Produces an OrderedList component.
     * @return The ordered list component
     */
    public static OrderedList ol(){
        return new OrderedList();
    }
    /**
     * Produces an UnorderedList component.
     * @return The unordered list component
     */
    public static UnorderedList ul(){
        return new UnorderedList();
    }
}
