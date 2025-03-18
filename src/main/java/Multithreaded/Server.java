package Multithreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {


    public Consumer<Socket> getConsumer(){
        return (clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                toClient.println("HTTP/1.1 200 OK");
                fromClient.readLine();
                toClient.close();
                fromClient.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public void run() throws IOException {
        int port = 8010;
        Server server = new Server();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(100000);
            do {
                System.out.println("waiting for client");
                Socket acceptedConnection = serverSocket.accept();
                System.out.println("Connection accepted from " + acceptedConnection.getRemoteSocketAddress() + " on port " + acceptedConnection.getPort());
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedConnection));
                System.out.println("Connection closed");
                System.exit(0);
            } while (true);
        }
    }
    public static void main(String[] args){
        Server server = new Server();
        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
