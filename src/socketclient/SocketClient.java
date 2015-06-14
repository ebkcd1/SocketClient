package socketclient;

/**
 * Group 8 Project 1 CNT 4504 Client Application Stations used:
 * Client:192.168.100.115 Server: 192.168.100.116
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SocketClient implements Runnable {

    private static String serverHost;
    private static int pid = 6544;
    private static int select = 0;
    private static long total;
    private static double avg;
    private static int finishedThreads = 0;

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter server hostname: ");
        serverHost = scan.nextLine();

        System.out.println("Attemping to connect to host "
                + serverHost + " on port " + pid + ".");

        do {

            System.out.println("Enter the number of Clients");
            int numOfClients = scan.nextInt();

            select = displayMenu();

            if (select >= 1 && select < 7) {

                //creates the client threads array 
                Thread clients[] = new Thread[numOfClients];

                //delcares the client threads 
                for (int i = 0; i < numOfClients; i++) {
                    clients[i] = new Thread(new SocketClient());
                }

                //starts the threads 
                for (int x = 0; x < numOfClients; x++) {
                    clients[x].start();
                }
                while (finishedThreads != numOfClients) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                    }
                }

                avg = total / (double) numOfClients;
                System.out.println("Total Response Time:" + total + " for " + numOfClients + " clients.");
                System.out.println("Average Response time: " + avg + " ms");
                avg = 0;
                total = 0;
                select = 0;
                finishedThreads = 0;
            }//end if check
            else {
                System.out.println("");
            }
        } while (select != 7);

    }//End of main

    public static void command(int select) throws IOException {
        long start = System.currentTimeMillis();
        long response = 0;
        Socket netSocket = null;
        PrintWriter outStream = null;
        BufferedReader inStream = null;

        netSocket = new Socket(serverHost, pid);
        outStream = new PrintWriter(netSocket.getOutputStream(), true);
        inStream = new BufferedReader(new InputStreamReader(netSocket.getInputStream()));

        outStream.println(select);
        String line = inStream.readLine();

        while (line != null && !line.equals("bye")) {

            System.out.print(line + "\n");
            line = inStream.readLine();

        }
        
        long end = System.currentTimeMillis();
        response = end - start;

        total = response + total;
        finishedThreads++;
        netSocket.close();
        outStream.close();
        inStream.close();
    }// end command()

    public static int displayMenu() throws IOException {

        boolean x = true;
        Scanner scan = new Scanner(System.in);
        String userInput = "";
        do {
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
                x = false;
            }
            if (select > 7 || select <= 0) {
                System.out.println("Pick a number between 1 and 7.");
                x = false;
            }
        } while (!x);
        return select;

    }// end displayMenu()

    @Override
    public void run() {
        try {
            command(select);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }//end run()
}//end SocketClient class
