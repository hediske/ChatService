package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clientHandler extends Thread{
    private Socket clientSocket;
    private chatServer chatServer;
    private PrintWriter out;


    public clientHandler(Socket socket , chatServer server){
        this.chatServer= server;
        this.clientSocket= socket;
    }

    public void sendMessage(String message) {
        out.println(message);
        out.flush();
    }
    @Override
    public void run(){
        try{
            out = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String input ;
            while((input = in.readLine())!=null){
                System.out.println("Sending a message brodcast "+input);
                chatServer.broadCast(input, clientHandler.this);
            }
            clientSocket.close();

        }
        catch(Exception e ){
            e.printStackTrace();
        }
    }
}
