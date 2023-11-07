package Networking;

import java.util.ArrayList;

/**
 * Class to manage all {@link HTTPConnection} objects.
 */
public class ConnectionChecker {
    private static int totalRegistered = 0;
    private static ArrayList<HTTPConnection> connections = new ArrayList<>();

    /**
     * Registers a new connection to be managed.
     * @param connection The new connection
     */
    protected static void registerConnection(HTTPConnection connection){
        totalRegistered++;
        connections.add(connection);
        connection.registerId(totalRegistered);
    }
    /**
     * Releases a connection from management.
     * <p> WARNING: This should not be called manually by users without extensive ensurement that the connection is disposed.
     * @param connection The connection to release
     */
    protected static void removeConnection(HTTPConnection connection){
        connections.remove(connection);
    }

    /**
     * Checks if there are any remaining active connections and if any are found, they are disposed of.
     */
    public static void disposeAll(){
        if (connections.isEmpty()){
            return;
        }

        System.out.printf("%d open connections found. Disposing...\n", connections.size());
        while(!connections.isEmpty()){
            connections.remove(0).dispose();
        }
        System.out.println("All remaining connections disposed.");
    }
}
