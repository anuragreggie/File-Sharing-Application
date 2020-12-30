import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;
    private ServerSocket serverSocket;

    /**
     * Sets the port for the server object.
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * This is the method that runs the server
     * @throws IOException
     */
    public void runServer() throws IOException, FileNotFoundException {

        ConnectionHandler ch = new ConnectionHandler(); //ch checks the input to check for disconnection.
        serverSocket = new ServerSocket(port);
        System.out.println("Waiting for client.....");
        Socket socket = serverSocket.accept(); // wait for client to connect.
        System.out.println();
        System.out.println("Accepted connection : " + socket);

        // in Stores the data sent by the client.
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // serverInput stores the keyboard input of the user acting as the server.
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));

        String viewChoice = in.readLine();
        try {
            ch.checkInput(viewChoice);// check the input to see if client has disconnected.
        } catch (DisconnectedException e) {
            e.printStackTrace();
            ch.cleanup(in, serverInput, socket);
        }

        if (viewChoice.equals("V")) {

            System.out.println("-- Please specify the directory you want to make available for sharing --");
            String dirs = serverInput.readLine();
            try {
                ch.checkInput(dirs);//check the input to see if server has disconnected.
            } catch (DisconnectedException e) {
                e.printStackTrace();
                ch.cleanup(in, serverInput, socket);
            }
            // Send the list of files specified by the server over to the client.
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(dirs);

            System.out.println();
            System.out.println("Waiting for client to pick the file....");
            // Read in the file that the client picked and display it to the sender.
            String filePath = in.readLine();
            try {
                ch.checkInput(filePath);
            } catch (DisconnectedException e) {
                e.printStackTrace();
                ch.cleanup(in, serverInput, socket);
                System.exit(1);
            }
            System.out.println(" ");
            System.out.println("Client has picked:" + filePath);

            System.out.println("Waiting for client to specify directory to save file in....");
            System.out.println("-- Are you sure you want to share this? If not type \"exit\", otherwise press any key --");
            String choice = serverInput.readLine();
            // Check the choice to make sure sender wants to send the file.
            try {
                ch.checkInput(choice);// check the input to see if client has disconnected.
            } catch (DisconnectedException e) {
                e.printStackTrace();
                ch.cleanup(in, serverInput, socket);
            }

            // Read in the path that the client wants to save the file to.
            String savePath = in.readLine();
            try {
                ch.checkInput(savePath);// check the input to see if client has disconnected.
            } catch (DisconnectedException e) {
                e.printStackTrace();
                ch.cleanup(in, serverInput, socket);
            }

            System.out.println("Client is now done");

            // Read the bytes of the file into an array from 0 to the length of the file.
            File transferFile = new File (filePath);
            if (!transferFile.isDirectory()) {
                out.println("Invalid file!");
            }
            byte [] bytearray = new byte [(int)transferFile.length()];
            FileInputStream fin = new FileInputStream(transferFile);
            BufferedInputStream bin = new BufferedInputStream(fin);
            bin.read(bytearray,0, bytearray.length);
            // Send the bytes over to the client.
            OutputStream os = socket.getOutputStream();

            System.out.println("Sending Files...");
            os.write(bytearray,0, bytearray.length);
            os.flush();
            socket.close();
            System.out.println(" ");

        }

        else {
            //Otherwise we do the same process but we do not need to print specify the files being shared.
            String filePath = in.readLine();
            try {
                ch.checkInput(filePath);
            } catch (DisconnectedException e) {
                e.printStackTrace();
                ch.cleanup(in, serverInput, socket);
                System.exit(1);
            }
            System.out.println("Client has picked:" + filePath);

            System.out.println("Waiting for client to specify directory to save file in....");
            System.out.println("-- Are you sure you want to share this? If not type \"exit\", otherwise press any key --");
            String choice = serverInput.readLine();
            try {
                ch.checkInput(choice);
            } catch (DisconnectedException e) {
                e.printStackTrace();
                ch.cleanup(in, serverInput, socket);
                System.exit(1);
            }
            String savePath = in.readLine();
            try {
                ch.checkInput(savePath);// check the input to see if client has disconnected.
            } catch (DisconnectedException e) {
                e.printStackTrace();
                ch.cleanup(in, serverInput, socket);
                System.exit(1);
            }
            System.out.println("Client is now done");

            File transferFile = new File (filePath);
            byte [] bytearray = new byte [(int)transferFile.length()];
            FileInputStream fin = new FileInputStream(transferFile);
            BufferedInputStream bin = new BufferedInputStream(fin);
            bin.read(bytearray,0, bytearray.length);
            OutputStream os = socket.getOutputStream();


            System.out.println("Sending Files...");
            os.write(bytearray,0, bytearray.length);
            os.flush();
            socket.close();

        }
        System.out.println("File transfer complete");

    }

}

