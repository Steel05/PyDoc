import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import Utils.FileUtils;

public class Parser{
    public static final class Constants{
        public static final String SEARCH_REGEX = "\"\"\"d[\\.\\w\\s:-]+\"\"\"\\n\\s*((class)|(def)) \\w+";
        public static final String COMMENTFIND_REGEX = "(?<=\"\"\"d\\n)[\\.\\w\\s-:]+(?=\\n\\s\"\"\"\\n)";
        public static final String FUNCTIONFIND_REGEX = "(?<=def )[\\w-]+(?=\\([\\s\\S]*\\):)";
        public static final String CLASSFIND_REGEX = "(?<=class )[\\w-]+(?=[:(])";
        public static final String PARAMFIND_REGEX = "(?<=:param )[^\\n]+";
        public static final String RETURNFIND_REGEX = "(?<=:return )[^\\n]+";
        public static final String TYPEFIND_REGEX = "(?<= :- )[^\\n]+";
    }

    public static void main(String[] args) {
        String content = loadFile();
        Matcher matcher = Pattern.compile(Constants.SEARCH_REGEX).matcher(content);

        StringBuilder outBuilder = new StringBuilder();
        while (matcher.find()){
            outBuilder.append(content.subSequence(matcher.start(), matcher.end()) + "\n\n");
        }

        FileUtils.dumpString(outBuilder.toString(), "parseResults.txt");
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