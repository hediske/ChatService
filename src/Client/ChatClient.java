package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    
    public static void main(String[] args) {
        try {
            
            Socket socket = new Socket("localhost", 12345);
            System.out.println("successfully connected to server on port "+12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Thread receiverThread = new Thread(() -> {
                try {
                    System.out.println("started receiving messages ");
                    BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String serverResponse;
                    while ((serverResponse = serverIn.readLine()) != null) {
                        System.out.println("Received a message "+serverResponse);
                    }
                    System.out.println("stopped receiving messages");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiverThread.start();

            String userInput;
            while ((userInput = in.readLine()) != null) {
                System.out.println("sending message to the global channel : "+userInput);
                out.println(userInput);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
