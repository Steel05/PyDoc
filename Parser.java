import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import Utils.FileUtils;

public class Parser{
    public static final String SEARCH_REGEX = "\"\"\"d[\\.\\w\\s;:-]+\"\"\"\\n\\s*((class)|(def)) [\\w+\\s(),]+:";

    public static void main(String[] args) {
        String content = loadFile();
        Matcher matcher = Pattern.compile(SEARCH_REGEX).matcher(content);

        ArrayList<PyDocComment> comments = new ArrayList<>();
        while (matcher.find()){
            comments.add(new PyDocComment((String)content.subSequence(matcher.start(), matcher.end())));
        }

        StringBuilder debug = new StringBuilder();
        for (PyDocComment doc : comments){
            debug.append(doc.toString() + "\n\n");
        }

        FileUtils.dumpString(debug.toString(), "parse.txt");
    }

    private static String loadFile(){
        JFileChooser chooser = new JFileChooser();
        int response = chooser.showOpenDialog(null);
        if (response == JFileChooser.CANCEL_OPTION || response == JFileChooser.ERROR_OPTION){
            System.err.println("No file to open");
        }
        File file = chooser.getSelectedFile();
        System.out.printf("Beginning parse on %s", file.getName());
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
            return"";
        }
    }
}