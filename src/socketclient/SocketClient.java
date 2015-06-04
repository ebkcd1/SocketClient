package socketclient;

/**
 * Group 8 Project 1 CNT 4504 Client Application Stations used: Client:
 * 192.168.100.115 Server: 192.168.100.116
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) throws IOException {

        String serverHost;

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter server hostname: ");
        serverHost = scan.nextLine();
        int pid = 6544;

        if (args.length > 0) {
            serverHost = args[0];
        }
        System.out.println("Attemping to connect to host "
                + serverHost + " on port " + pid + ".");

        Socket netSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            netSocket = new Socket(serverHost, pid);
            out = new PrintWriter(netSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(netSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Cannot connect to host: " + serverHost);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not get I/O for "
                    + "the connection to: " + serverHost);
            System.exit(1);
        }

        String userInput = "";
        int select = 0;
        //BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        do {
       
            System.out.println("Enter a command: ");
            System.out.println("1.  Host current Date and Time");
            System.out.println("2.  Host uptime");
            System.out.println("3.  Host memory use");
            System.out.println("4.  Host Netstat");
            System.out.println("5.  Host current users");
            System.out.println("6.  Host running processes");
            System.out.println("7.  Quit");
                    
                userInput = scan.next();
                boolean check = isNumeric(userInput);
                if(check){
                    select = Integer.parseInt(userInput);
                    out.println(select);
                    System.out.println("echo: " + in.readLine());
                    System.out.println(in.readLine());
                }
                else{
                    System.out.println("Please enter a number between 1 and 7");
                }

        } while (select > 0 || select <= 6);
       
        if(select == 7){
        out.close();
        in.close();
        //stdIn.close();
        netSocket.close();
        }
    }

    public static int displayMenu() throws IOException {
        int select = 0;
        Scanner scan = new Scanner(System.in);
        String userInput = "";

        System.out.println("Enter a command: ");
        System.out.println("1.  Host current Date and Time");
        System.out.println("2.  Host uptime");
        System.out.println("3.  Host memory use");
        System.out.println("4.  Host Netstat");
        System.out.println("5.  Host current users");
        System.out.println("6.  Host running processes");
        System.out.println("7.  Quit");

        try {
            userInput = scan.next();
            select = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a number between 1-7");
        }
        return select;
    }
    public static boolean isNumeric(String input){
        
            try{
                int select = Integer.parseInt(input);
                }
                catch(NumberFormatException nfe){
                    return false;
                }
            
        return true;
    }
    /**
     * public static String sendCommand(String serverHost,int pid,int cmd){
     * String response; BufferedReader stdIn = new BufferedReader(new
     * InputStreamReader(System.in)); String userInput = stdIn.readLine();
     * Socket netSocket = null; PrintWriter out = null; BufferedReader in =
     * null;
     *
     * try { netSocket = new Socket(serverHost, pid); out = new
     * PrintWriter(netSocket.getOutputStream(), true); in = new
     * BufferedReader(new InputStreamReader(netSocket.getInputStream())); }
     * catch (UnknownHostException e) { System.err.println("Cannot connect to
     * host: " + serverHost); System.exit(1); } catch (IOException e) {
     * System.err.println("Could not get I/O for " + "the connection to: " +
     * serverHost); System.exit(1); } out.println(userInput);
     * System.out.println("echo: " + in.readLine());
     * System.out.println(in.readLine()); System.out.println(in.readLine());
     * return response;
    }*
     */
}
