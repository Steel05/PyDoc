package Networking;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class HTTPResponse {
    private int responseCode = -1;
    private Map<String, List<String>> responseFields;
    private String content;

    protected HTTPResponse(int responseCode, Map<String, List<String>> responseFields, String content){
        this.responseCode = responseCode;
        this.responseFields = responseFields;
        this.content = content;
    }

    public boolean isGood(){
        return responseCode == HttpURLConnection.HTTP_OK;
    }

    public int getResponseCode(){
        return responseCode;
    }
    public Map<String, List<String>> getResponseFields(){
        return responseFields;
    }
    public String getContent(){
        return content;
    }
}
