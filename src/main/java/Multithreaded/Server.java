package Multithreaded;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {


    public Consumer<Socket> getConsumer() {
        return (clientSocket) -> {
            try (PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true)){
                // Send a complete HTTP response
                toClient.println("Hello from server " + clientSocket.getInetAddress());
                // Close resources
                toClient.close();
                clientSocket.close();
                System.out.println("Connection is Closed !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args){
        int port = 8010;
        Server server = new Server();
        try (ServerSocket serverSocket = new ServerSocket(port)){
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is Listening on Port : " + port);
            while (true){
                Socket clientSocket = serverSocket.accept();
                // Create and start a new thread for each client
                Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
