import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args) {

        // This is the scanner object used to take in keyboard inputs from the user
        Scanner s = new Scanner(System.in);

        String choice;
        // Do while loop is used to continuously ask the user to input a valid choice.
        do {
            System.out.println("*** WELCOME TO SFS ***");
            System.out.println("-- If you want to exit at any point type \"exit\" --");
            choice = s.next();
            // Check for exit string at any point in main.
            checkForExitString(choice);

            if (choice.toLowerCase().equalsIgnoreCase("SEND")){
                System.out.println("You have chosen to send a file");
                System.out.println("-- Please enter the port number --");
                // Take the port as a String first to check for exit string and then cast it to an integer.
                if (!s.hasNextInt()) {
                    System.out.println("This has to be an integer!");
                    System.exit(1);
                }

                String input = s.next();
                checkForExitString(input);
                int port = Integer.parseInt(input);
                // Check if the port is in the valid range
                if (port < 1 || port > 65535) {
                    System.out.println("Invalid port!");
                    System.exit(1);
                }

                // Create a new server object and set its port
                Server server = new Server();
                server.setPort(port);

                // Try to run the server and catch an IOException.
                try {
                    server.runServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            else if (choice.toLowerCase().equalsIgnoreCase("GET")) {

                System.out.println("You have chosen to receive a file");
                // Create a new client object.
                Client client = new Client();

                String ipAddress;

                System.out.println("-- Please enter the IP Address of the sender --");

                ipAddress = s.next();
                // Check if the IP Address is valid using regex.
                Matcher matcher = Configuration.pattern.matcher(ipAddress);
                if (!matcher.matches()) {
                    System.out.println("Invalid IP Address!");
                    System.exit(1);
                }

                // Check if user wants to exit.
                checkForExitString(ipAddress);

                // Set the IP Address of the client
                client.setIpAddress(ipAddress);

                System.out.println("-- Please enter the port number of the port you want to connect to --");
                if (!s.hasNextInt()) {
                    System.out.println("This has to be an integer!");
                    System.exit(1);
                }
                String input = s.next();
                checkForExitString(input);
                int port = Integer.parseInt(input);
                // check if the port is in the valid range.
                if (port < 1 || port > 65535) {
                    System.out.println("Invalid port!");
                    System.exit(1);
                }
                // Set the port number of the client and then run the client.
                client.setPort(port);

                try {
                    client.runClient();

                } catch (FileNotFoundException fileNotFoundException) {
                    System.out.println("File not found");
                }
            }

        } while (!(choice.toLowerCase().equals("get") || choice.toLowerCase().equals("send")));

    }

    /**
     * Method to check for the exit string at any point in main.
     * @param input - The user input
     */
    public static void checkForExitString(String input) {
        if (input.equalsIgnoreCase(Configuration.exitString)){
            System.exit(1);
        }
    }

}