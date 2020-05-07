
/** CLIENT                                                  February 2019 DL 08/03/19
 *
 * This Client program asks the user to input commands to be sent to the server.
 *
 * There are only two valid commands in the protocol: "Time" and "Echo"
 *
 * If user types "Time" the server should reply with the current server time.
 *
 * If the user types "Echo" followed by a message, the server will echo back the message.
 * e.g. "Echo Nice to meet you"
 *
 * If the user enters any other input, the server will not understand, and
 * will send back a message to the effect.
 *
 * NOte: You must run the server before running this the client.
 * (Both the server and the client will be running together on this computer)
 */
package oop_ca6_aislingsmith;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

public class Client
{
    public static void main(String[] args)
    {
        Client client = new Client();
        client.start();
    }

    public void start()
    {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8081);  // connect to server socket, and open new socket

            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort() );

            System.out.println("Client: This Client is running and has connected to the server");

            System.out.println("[Commands: \"Heartbeat\" to get time, or \"GetRegisteredVehicles\" to get registered vehicles)]");
            System.out.println("Please enter a command: ");
            
            String command = in.nextLine();  // read a command from the user

            OutputStream os = socket.getOutputStream();

            PrintWriter socketWriter= new PrintWriter(os, true);// true=> auto flush buffers
            socketWriter.println(command);  // write command to socket
          
            Scanner socketReader = new Scanner(socket.getInputStream());  
            
            if(command.startsWith("Heartbeat")){
                JsonBuilderFactory factory = Json.createBuilderFactory(null);
                
                JsonObject jsonRootObject
                = Json.createObjectBuilder()
                        .add("packetType", "Heartbeat")
                        .build();
                
                String value = jsonRootObject.toString();
                socketWriter.println(value);
                
                System.out.println("Client Request:" + value);
                
                String response = socketReader.nextLine();
                System.out.println("Client Response from Server: \"" + response + "\"");   
            }
            else if(command.startsWith("GetRegisteredVehicles")){
                JsonBuilderFactory factory = Json.createBuilderFactory(null);
                
                 JsonObject jsonRootObject
                = Json.createObjectBuilder()
                        .add("packetType", "GetRegisteredVehicles")
                        .build();
                
                String value = jsonRootObject.toString();
                socketWriter.println(value);
                
                System.out.println("Client Request:" + value);
                
                String response = socketReader.nextLine();
                System.out.println("Client Response from Server: \"" + response + "\"");   
                
            }
            else{
                String input = socketReader.nextLine();
                System.out.println("Client Response from Server: \"" + input + "\"");   
            }
            


            socketWriter.close();
            socketReader.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }
}
