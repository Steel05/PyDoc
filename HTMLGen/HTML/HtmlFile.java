package HTML;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.Optional;

import CSS.CSSFile;
import HTML.Components.Component;
import HTML.Components.BaseComponents.Body;
import HTML.Components.BaseComponents.Head;
import HTML.Components.BaseComponents.Html;
import HTML.Components.BaseComponents.RawText;
import Interfaces.Savable;
import Utils.FileUtils;

/**
 * Represents an HTML file in memory.
 */
public class HtmlFile implements Savable{
    private final Component htmlComponent;
    private final Component head;
    private final Component body;

    private boolean usesCSS = false;
    private CSSFile cssFile = null;

    public HtmlFile(String title){
        htmlComponent = new Html();
        body = new Body();
        head = new Head();
        htmlComponent.addChildren(head, body);

        head.addChild(new RawText(String.format("<title>%s</title>", title)));
    }

    /**
     * Binds a constructs a CSS file and binds it to this HTML file.
     * <p>NOTE: The user does not need to call {@code save()} on the returned
     * file. The file is saved automatically with the name {@code styles.css} when this file is saved.
     * @return The newly constructed CSS file
     */
    public CSSFile createCSSFile(){
        usesCSS = true;
        cssFile = new CSSFile();
        return cssFile;
    }

    public Component getHTMLComponent(){
        return htmlComponent;
    }
    public Component getHeadComponent(){
        return head;
    }
    public Component getBodyComponent(){
        return body;
    }

    /**
     * Writes the HTML code contained within to a file. 
     * @param fileName The name of the output file
     */
    public void save(String fileName){
        if (!ComponentChecker.clearToSave()){
            System.out.println("Saving cancelled due to free components.");
            return;
        }

        if (usesCSS){
            String refLink;
            Optional<String> cssName = cssFile.getSaveName();

            if (cssName.isEmpty()){
                cssFile.save("styles.css");
                refLink = "./styles.css";
            }
            else{
                refLink = "./" + cssName.get();
            }
        
            head.addChild(new RawText(String.format("<link rel=\"stylesheet\" href=\"%s\">", refLink)));
        }

        Optional<String> saveName = FileUtils.parseFileName(fileName, "html");

        if (saveName.isEmpty()){
            return;
        }

        try{
            writeFile(saveName.get());
        }
        catch (Exception e){
            System.err.println("Error saving file");

            e.printStackTrace();
        }
    }

    /**
     * Writes the contents to a file with the appropriate file name.
     * @param fileName The name of the file to write to
     * @throws Exception
     */
    private void writeFile(String fileName) throws Exception{
        DataOutputStream file;

        file = new DataOutputStream(new FileOutputStream(fileName));

        char[] data = htmlComponent.write().toCharArray();

        //Write
        for (char character : data){
            file.write(character);
        }

        file.close();
    }
}