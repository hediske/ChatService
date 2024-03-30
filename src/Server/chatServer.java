package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class chatServer {

    private ServerSocket serverSocket ;
    private int port; 
    private Logger logger = Logger.getLogger(getClass().getName());
    private List<clientHandler> clients = new CopyOnWriteArrayList<>();


    public void start (int port) throws IOException{
            this.port = port;
            serverSocket= new ServerSocket(port);
            logger.info("server successfully created and running on port! "+port);    
            if(serverSocket!=null){
            while (true) {
                Socket clientSocket= serverSocket.accept();
                clientHandler clientHandler = new clientHandler(clientSocket, chatServer.this);
                clients.add(clientHandler);
                clientHandler.start();
            }
        }
    }

    public void broadCast(String message,clientHandler sender){
        clients.stream().forEach((clientHandler client ) -> {if (client != sender) {
            client.sendMessage(message);
        System.out.println("sending to client "+client.getId());}
        });
    }
    public static void main(String[] args) {
        chatServer server = new chatServer();
        try{
            server.start(12345);
        }
        catch(Exception e ){
            server.logger.log(Level.SEVERE, "error in the creation of the server", e.getMessage());
            e.printStackTrace();
        }
    }
}
