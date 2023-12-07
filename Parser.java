import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import CSS.CSSClass;
import CSS.CSSFile;
import CSS.CSSRule;
import CSS.CSSUnit;
import HTML.HtmlFile;
import HTML.Components.Component;
import HTML.Components.BaseComponents.Header;
import HTML.Components.BaseComponents.UnorderedList;
import HTML.Tags;

public class Parser{
    public static void main(String[] args) {
        HtmlFile file = new HtmlFile("PyDoc");
        Component baseComponent = file.getBodyComponent();

        CSSFile cssFile = file.createCSSFile();
        CSSClass classList = cssFile.createClass(".class-List").addRules(
            new CSSRule("list-style", "none"));
        CSSClass customDivider = cssFile.createClass(".divider").addRules(
            new CSSRule("height", "2", CSSUnit.px),
            new CSSRule("width", "90", CSSUnit.percent),
            new CSSRule("margin", "auto"),
            new CSSRule("background-color", "grey"));

        CSSClass jClass = cssFile.createClass(".java-class").addRules(
            new CSSRule("padding", "5", CSSUnit.px),
            new CSSRule("padding-left", "10", CSSUnit.px));
        CSSClass beige = cssFile.createClass(".beige-class").addRules(
            new CSSRule("background-color", "beige"));
        CSSClass antiqueWhite = cssFile.createClass(".white-class").addRules(
            new CSSRule("background-color", "antiquewhite"));

        PyDocComment[] docs = getDocs();
        HashMap<String, Component> classMapFunctions = new HashMap<String, Component>();
        HashMap<String, Component> classMapProperties = new HashMap<String, Component>();

        boolean isBeige = true;

        for (PyDocComment doc : docs){
            if (doc.describesClass()){
                isBeige = !isBeige;
                Component docComponent = formatDocToHTML(doc);
                docComponent.addCSSClasses(new CSSClass[] {jClass, isBeige ? beige : antiqueWhite});
                Component list = Tags.ul().addCSSClasses(classList);
                baseComponent.addChild(docComponent.addChildren(Tags.h3("Functions"), list));
                Component properties = Tags.ul().addCSSClasses(classList);
                docComponent.addChildren(Tags.h3("Properties"), properties);
                classMapFunctions.put(doc.name(), list);
                classMapProperties.put(doc.name(), properties);
                baseComponent.addChild(Tags.hr());
            }
            else{
                if(doc.containedInClass()){
                    if (doc.describesFunction()){
                        Component list = classMapFunctions.get(doc.parentClassName());
                        if (list.getChildren().length > 0){
                            list.addChild(Tags.div().addCSSClasses(customDivider));
                        }

                        classMapFunctions.get(doc.parentClassName()).addChild(Tags.div().addChild(formatDocToHTML(doc)));
                    }
                    else{
                        Component list = classMapProperties.get(doc.parentClassName());
                        if (list.getChildren().length > 0){
                            list.addChild(Tags.div().addCSSClasses(customDivider));
                        }

                        classMapProperties.get(doc.parentClassName()).addChild(Tags.div().addChild(formatDocToHTML(doc)));
                    }
                    
                }
                else{
                    baseComponent.addChild(formatDocToHTML(doc));
                    baseComponent.addChild(Tags.hr());
                }
            }
        }

        for (Component list : classMapProperties.values()){
            if (list.getChildren().length == 0){
                Component[] children = list.getParent().getChildren();
                Component h = children[children.length - 2];
                h.release();
                list.release();
            }
        }

        file.save("index.html");
    }

    private static Component formatDocToHTML(PyDocComment doc){
        Component master = Tags.div();

        String type = "";
        if(doc.describesClass()){
            type = "Class";
        }
        else if(doc.describesFunction()){
            type = "Function";
        }
        else if(doc.describesProperty()){
            type = "Property";
        }

        master.addChildren(Tags.h3(doc.name() + " : " + type), 
            Tags.p(doc.description()));

        if (doc.describesClass()){
            if (doc.getSupportedOperators().isEmpty() || doc.getSupportedOperators().isBlank()){
                return master;
            }
            return master.addChild(Tags.p("Supported Operators: " + doc.getSupportedOperators()));
        }

        if (doc.describesFunction()){
            if (doc.parameters().length != 0){
            UnorderedList list = Tags.ul();
            master.addChild(new Header(4, "Parameters").addChild(list));
        
            for (String parameter : doc.parameters()){
                String[] info = doc.getParamInfo(parameter);
                list.addChild(Tags.rawText(String.format("<i>%s</i> : %s - %s", parameter, info[1], info[0])));
            }
            }

            String returnDescription = doc.returnDescription().isEmpty() ? "" : ("  - " + doc.returnDescription());

            if (doc.name().startsWith("__") && doc.name().endsWith("__")){
                return master;
            }

            master.addChild(Tags.p(String.format("<b>Return</b>: %s%s", doc.returnType(), returnDescription)));

            return master;
        }

        master.addChild(Tags.p("Type: " + doc.returnType()));
        return master;
    }

    private static PyDocComment[] getDocs(){
        String currentClass = "None";
        String content = loadFile();
        Matcher matcher = Pattern.compile(PyDocComment.RegEx.COMMENTSEARCH).matcher(content);

        ArrayList<PyDocComment> comments = new ArrayList<>();
        while (matcher.find()){
            PyDocComment comment = new PyDocComment(content.substring(matcher.start(), matcher.end()));
            comments.add(comment);
            
            if (comment.describesClass()){
                currentClass = comment.name();
            }
            else{
                if (content.charAt(matcher.start() - 1) == ' '){
                    comment.bindParentClass(currentClass);
                }
            }
        }

        return comments.toArray(new PyDocComment[comments.size()]);
    }

    private static String loadFile(){
        JFileChooser chooser = new JFileChooser();
        int response = chooser.showOpenDialog(null);
        if (response == JFileChooser.CANCEL_OPTION || response == JFileChooser.ERROR_OPTION){
            System.err.println("No file to open");
        }
        File file = chooser.getSelectedFile();
        StringBuilder contentBuilder = new StringBuilder();
        String content;
        try {
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNext()){
                contentBuilder.append(fileReader.nextLine()+ "\n");
            }
            fileReader.close();

            content = contentBuilder.toString();
            if (content.isBlank() || content.isEmpty()){
                System.err.println("Blank scan");
                return "";
            }

            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}