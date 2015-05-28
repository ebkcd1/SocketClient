
package socketclient;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        
        String serverHost;

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter server hostname: ");
        serverHost = scan.nextLine();
               
        
        if (args.length > 0)
           serverHost = args[0];
        System.out.println ("Attemping to connect to host " +
		serverHost + " on port 25000.");

        Socket netSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            netSocket = new Socket(serverHost, 25000);
            out = new PrintWriter(netSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        netSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Cannot connect to host: " + serverHost);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not get I/O for "
                               + "the connection to: " + serverHost);
            System.exit(1);
        }

	BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in));
	String userInput;

        System.out.println("Enter a command: ");
        System.out.println("1.  Host current Date and Time");
        System.out.println("2.  Host uptime");
        System.out.println("3.  Host memory use");
        System.out.println("4.  Host Netstat");
        System.out.println("5.  Host current users");
        System.out.println("6.  Host running processes");
        System.out.println("7.  Quit");
	while ((userInput = stdIn.readLine()) != null) {
	    out.println(userInput);
	    System.out.println("echo: " + in.readLine());
          
	}

	out.close();
	in.close();
	stdIn.close();
	netSocket.close();
    }
}

