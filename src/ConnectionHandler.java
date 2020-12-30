// The code here was originally from Studres, but it has been modified.
// https://studres.cs.st-andrews.ac.uk/CS2101/Examples/CS2101_BasicClientServerExample/src/.


import java.io.*;
import java.net.Socket;

public class ConnectionHandler {
    /**
     * Checks for disconnections.
     * @param line - the input
     * @throws DisconnectedException - when the line is null
     */
    public void checkInput(String line) throws DisconnectedException {
        // if readLine fails we can deduce here that the connection to the client is broken
        // and shut down the connection on this side cleanly by throwing a DisconnectedException
        // which will be passed up the call stack to the nearest handler (catch block)
        // in the run method
        if(line == null || line.equals("null") || line.equals(Configuration.exitString) ){
            throw new DisconnectedException(" ... connection has been closed ... ");

        }
    }

    /**
     * Close all the buffered readers and socket to clean up cleanly.
     * @param br1 - buffered reader for keyboard input
     * @param br2 - buffered reader for input stream.
     * @param socket - socket that is used for the connection.
     */
    public void cleanup(BufferedReader br1, BufferedReader br2, Socket socket) {
        System.out.println("ConnectionHandler: ... cleaning up and exiting ... " );
        try{
            br1.close();
            br2.close();
            socket.close();
        } catch (IOException ioe){
            System.out.println("ConnectionHandler:cleanup " + ioe.getMessage());
        }
    }

}
