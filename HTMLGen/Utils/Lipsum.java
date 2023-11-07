package Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Networking.HTTPConnection;
import Networking.HTTPResponse;

/**
 * Contains methods for using 'Lorem Ipsum' placeholder text
 */
public class Lipsum {
    private static final String PARSING_REGEX = "(?<=<div id=\"lipsum\">)(<p>[\\s\\S]+<\\/p>)(?=<\\/div>)";
    private static final String SPLITTING_REGEX = "<\\/?p>";

    /**
     * Retrieves text from <a href="http://www.lipsum.com">here</a>.
     * @param paragraphs The number of paragraphs to generate
     * @return A String array in which each element is a paragraph
     */
    public static String[] getText(int paragraphs){
        try {
            HTTPConnection connection = new HTTPConnection("https://www.lipsum.com/feed/html");

            HashMap<String, String> params = new HashMap<>();
            params.put("amount", String.valueOf(paragraphs));
            params.put("what", "paras");
            params.put("start", "yes");
            params.put("generate", "Generate Lorem Ipsum");

            HTTPResponse response = connection.sendRequest(HTTPConnection.POST, params);

            Matcher matcher = Pattern.compile(PARSING_REGEX).matcher(response.getContent());
            
            matcher.find();
            String rawContent = response.getContent().substring(matcher.start(), matcher.end() + 1);
            String[] unrefinedSplit = rawContent.split(SPLITTING_REGEX);
            
            ArrayList<String> refinedSplit = new ArrayList<>(Arrays.asList(unrefinedSplit));

            int currentIndex = 0;
            while (currentIndex < refinedSplit.size()){
                if (refinedSplit.get(currentIndex).length() > 5){
                    currentIndex++;
                }
                else{
                    refinedSplit.remove(currentIndex);
                }
            }

            return refinedSplit.toArray(new String[paragraphs]);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] errOut = new String[paragraphs];
        Arrays.fill(errOut, "NULL");
        return errOut;
    }
}
