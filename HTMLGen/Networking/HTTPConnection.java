package Networking;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class HTTPConnection {
    public static final String GET = "GET";
    public static final String HEAD = "HEAD";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String CONNECT = "CONNECT";
    public static final String OPTIONS = "OPTIONS";
    public static final String TRACE = "TRACE";
    public static final String PATCH = "PATCH";

    private String url;
    private int id;

    private HttpURLConnection connection;

    private boolean connected = false;

    /**
     * Creates a new HTTP connection socket.
     * @param autoConnect Should a network connection automatically be established with the server on construction
     * @param url The URL to connect to
     * @throws IOException if an I/O exception occurs
     * @throws MalformedURLException if the parsed URL fails to comply with the specific syntax of the associated protocol
     */
    public HTTPConnection(String url) throws IOException, MalformedURLException{
        this.url = url;

        URL urlObj = new URL(url);
        connection = (HttpURLConnection) urlObj.openConnection();
    
        setConnectionTimeout(5000);
    }
    protected void registerId(int id){
        this.id = id;
    }

    /**
     * Sets a specified timeout value, in milliseconds, to be used.
     * <p>A timeout of zero is interpreted as an infinite timeout.
     * @param timeoutMilliseconds The connect timeout value in milliseconds
     */
    public void setConnectionTimeout(int timeoutMilliseconds){
        connection.setConnectTimeout(timeoutMilliseconds);
    }
    /**
     * Returns setting for connect timeout.
     * <p> 0 return implies that the option is disabled (i.e., timeout of infinity).
     * @return The set connection timeout in milliseconds
     */
    public int getConnectionTimeout(){
        return connection.getConnectTimeout();
    }
    /**
     * Retrieves this connection's id
     * @return The id of this connection
     */
    public int getId(){
        return id;
    }

    /**
     * Sends an HTTP POST request.
     * @param payload Any data payloads to send
     * @return The response of this request
     */
    public HTTPResponse sendRequest(String requestMethod, Map<String, String> payload){
        try {
            connection.setRequestMethod(POST);
        } catch (ProtocolException e) {
            System.err.printf("Error setting %s request method", POST);
            e.printStackTrace();
            return null;
        }
        
        StringBuilder dataBuilder = new StringBuilder();

        for (String key : payload.keySet()){
            dataBuilder.append(key + "=" + parseValue(payload.get(key)) + "&");
        }
        dataBuilder.deleteCharAt(dataBuilder.length() - 1);
        String payloadData = dataBuilder.toString();

        connection.setDoOutput(true);
        try {
            DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());
            outStream.writeBytes(payloadData);

            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        connect();
        HTTPResponse res = constructResponse();
        dispose();

        return res;
    }

    public void connect(){
        if (connected){
            return;
        }
        
        try{
            connection.connect();
        } catch(Exception e){
            System.err.println("Error establishing connection to:\n" + url);
        }
        connected = true;
    }
    public void dispose(){
        connection.disconnect();
        connected = false;
        ConnectionChecker.removeConnection(this);
    }

    private String parseValue(String value){
        if (!value.contains(" ")){
            return value;
        }
    
        String[] parts = value.split(" ");
        StringBuilder outBuilder = new StringBuilder();
        for (String part : parts){
            outBuilder.append(part + "+");
        }
        outBuilder.deleteCharAt(outBuilder.length() - 1);
        return outBuilder.toString();
    }
    private HTTPResponse constructResponse(){
        int responseCode = -1;
        StringBuilder contentBuilder = new StringBuilder();

        try {
            responseCode = connection.getResponseCode();

            Scanner reader = new Scanner(connection.getInputStream());
            while(reader.hasNextLine()){
                contentBuilder.append(reader.nextLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        

        return new HTTPResponse(responseCode, connection.getHeaderFields(), contentBuilder.toString());
    }

    public boolean isConnected(){
        return connected;
    }

    @Override
    public boolean equals(Object other){
        return HTTPConnection.class.isInstance(other) && ((HTTPConnection)other).getId() == this.getId();
    }
}
