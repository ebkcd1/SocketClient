package socketclient;

/**
 * Group 8 
 * Project 1
 * CNT 4504 Client Application 
 * Stations used: 
 * Client:192.168.100.115 
 * Server: 192.168.100.116
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SocketClient implements Runnable{
    private static String serverHost;
    private static int pid = 6544;
    private static int select = 0;
    
    public static void main(String[] args) throws IOException {

        //String serverHost;

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter server hostname: ");
        serverHost = scan.nextLine();
        //int pid = 6544;
        System.out.println("Enter the number of Clients");
        int numOfClients = scan.nextInt();
        if (args.length > 0) {
            serverHost = args[0];
        }
        System.out.println("Attemping to connect to host "
                + serverHost + " on port " + pid + ".");
/*
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
        */
        do {
             select = displayMenu();

            if(select >= 1 && select < 7){
                
                    
                Thread clients[] = new Thread[numOfClients]; //creates the client threads array
                //delcares the client threads
                for(int i =0; i < numOfClients; i++){
                    clients[i] = new Thread(new SocketClient());
                }
                //starts the threads
                for(int x =0; x < numOfClients; x++){
                    clients[x].start();
                }
                
              /*  out.println(select);      
                String line;
                line = in.readLine();
                while(!(line.equals("bye"))){
                    
                    System.out.println("echo: " + line);     
                    line = in.readLine(); 
                    if(line.equals("Goodbye!")){
                    System.exit(1);
                    }
                } */
               
               
  
            }//end if check
                
                else{
                    System.out.println("Please enter a number between 1 and 7");
                }

        } while (select != 7);
       
        
    }

    /*public static boolean isNumeric(String input){
        
            try{
                int select = Integer.parseInt(input);
                }
                catch(NumberFormatException nfe){
                    return false;
                }
            
        return true;
    }*/
    public static void command(int select, int port, String host) throws IOException{
    Socket netSocket = new Socket(serverHost, pid);
    PrintWriter out = new PrintWriter(netSocket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(netSocket.getInputStream()));
  
        out.println(select);
        String line = in.readLine();
        
       while(line != null){
           
           System.out.println(line + "\n");
           line = in.readLine();
       }
       //out.close();
       //netSocket.close();
        }// end command()
    
public static int displayMenu() throws IOException {
    
    boolean x = true;
    Scanner scan = new Scanner(System.in);
    String userInput = "";
        do{
        System.out.println("Enter a command: ");
        System.out.println("1.  Host current Date and Time");
        System.out.println("2.  Host uptime");
        System.out.println("3.  Host memory use");
        System.out.println("4.  Host Netstat");
        System.out.println("5.  Host current users");
        System.out.println("6.  Host running processes");
        System.out.println("7.  Quit");
        
        try{
            userInput = scan.next();
            select = Integer.parseInt(userInput);
        }
        catch(NumberFormatException e){
            System.out.println("Please enter a number between 1-7");
            x = false;
        }
        if(select > 7 || select <=0){
            System.out.println("Pick a number between 1 and 7.");
            x = false;
        }
        }while(!x);
        return select;
               
    }// end displayMenu()



    @Override
    public void run(){
       try{
            command(select,pid,serverHost);
        }
        catch(IOException io){
        io.printStackTrace();
        }
    }//end run()
}//end SocketClient class
