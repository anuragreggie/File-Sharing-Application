import java.io.*;
import java.net.Socket;

public class Client {

    private String ipAddress;
    private int port;
    private Socket socket;

    /**
     * Used to set the IP Address for the client object in main.
     * @param ipAddress - ip specified in main.
     */
    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }

    /**
     * Used to set the port for the client object in main.
     * @param port - port specified in main.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * This is the method that runs the client.
     * @throws IOException - when the file cannot be transferred properly.
     */
    public void runClient() throws FileNotFoundException {
        try {
            int fileSize = 10000000;
            int bytesRead;
            int currentTot;

            // construct a new socket.
            this.socket = new Socket(ipAddress, port);

            // buffered readers to get both keyboard input as well as the input stream.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));
            ConnectionHandler ch = new ConnectionHandler(); //ch checks the client input to check for disconnection.

            System.out.println("-- Would you like to view the files available to share? --");
            System.out.println("-- Type \"V\" to view, otherwise press any key to continue --");
            String viewChoice = clientInput.readLine();
            try {
                ch.checkInput(viewChoice);// check the input to see if client has disconnected.
            } catch (DisconnectedException e) {
                e.printStackTrace();
                ch.cleanup(in, clientInput, socket);
            }

            if (viewChoice.equals("V")){
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(viewChoice);
                System.out.println("Waiting for sender to specify the path of shared files....");
                String pathSpecified = in.readLine();
                System.out.println(" ");
                System.out.println("Sender has made this path available : " + pathSpecified);
                // print out the list of files in the machine.
                returnDirs(pathSpecified);
            }
            else {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(viewChoice);
            }

            System.out.println();
            System.out.println("-- Specify the path of the file you want to receive --");
            String filePath = clientInput.readLine();
            // tell the sender which file has been picked.
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(filePath);
            try {
                ch.checkInput(filePath);// check the input to see if client has disconnected.
            } catch (DisconnectedException e) {
                e.printStackTrace();
                ch.cleanup(in, clientInput, socket);
            }

            System.out.println();
            System.out.println("You picked " + filePath);

            System.out.println("-- Specify the path of where you want to save the file --");
            String savePath = clientInput.readLine();
            try {
                ch.checkInput(savePath);// check the input to see if server has disconnected.
            } catch (DisconnectedException e) {
                e.printStackTrace();
                ch.cleanup(in, clientInput, socket);
            }
            out.println(savePath);

            System.out.println("Waiting for sender to confirm....");

            // Code on how to read in bytes was from:
            // http://mrbool.com/file-transfer-between-2-computers-with-java/24516
            // Read bytes in from the input stream into a byte array.
            byte [] bytearray = new byte [fileSize];
            InputStream is = socket.getInputStream();
            FileOutputStream fos = new FileOutputStream(savePath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bytesRead = is.read(bytearray,0,bytearray.length);
            currentTot = bytesRead;

            // Bytes are read in until we finish reading all the bytes.
            do {
                bytesRead = is.read(bytearray, currentTot, (bytearray.length-currentTot));
                if(bytesRead >= 0) currentTot += bytesRead;
            } while(bytesRead > -1);

            // Bytes are written into the buffered output stream to create the file.
            bos.write(bytearray, 0 , currentTot);

            System.out.println("File received!");
            bos.flush();
            bos.close();
            socket.close();

        } catch (Exception e) { // exit cleanly for any Exception (including IOException, DisconnectedException)
            System.out.println("Ooops on connection to " + ipAddress + " on port " + port + ". " + e.getMessage());
        }

    }

    /**
     * This method converts the string containing the path to a File[] arr and calls recursive print on that.
     * @param pathOfSendFiles - path of the file that is being sent.
     */
    public void returnDirs(String pathOfSendFiles){
        File mainDir = new File(pathOfSendFiles);
        // array for files and sub-directories
        // of directory pointed by mainDir
        File[] arr = mainDir.listFiles();

        System.out.println("**********************************************");
        System.out.println("Files from main directory : " + mainDir);
        System.out.println("**********************************************");

        // Calling recursive method
        recursivePrint(arr, 0);
    }

    /**
     * Prints files on a machine
     * @param arr - name of the file
     * @param level - current level we are traversing
     */
    static void recursivePrint(File[] arr, int level) {
        // for-each loop for main directory files
        for (File f : arr)
        {
            // tabs for internal levels
            for (int i = 0; i < level; i++)
                System.out.print("\t");

            if(f.isFile())
                System.out.println(f.getName());

            else if(f.isDirectory())
            {
                // print name of the file
                System.out.println("[" + f.getName() + "]");

                // recursion for sub-directories
                recursivePrint(f.listFiles(), level + 1);
            }
        }
    }

}


