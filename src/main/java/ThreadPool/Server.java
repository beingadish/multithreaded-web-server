package ThreadPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    // Thread Pool for Serving the Requests
    private final ExecutorService threadPool;

    // Constructor for defining the PoolSize
    public Server(int poolSize){
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    private void handleClient(Socket clientSocket){
        try(PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true)){
            toClient.println("Hello from server " + clientSocket.getInetAddress());
            toClient.close();
            clientSocket.close();
            System.out.println("Connection is Closed !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        int port = 8010;
        // Defining the Pool Size
        int poolSize = 10;
        Server server = new Server(poolSize);

        try(ServerSocket serverSocket = new ServerSocket(port)){

            serverSocket.setSoTimeout(100000);
            System.out.println("Server is Listening on Port : " + port);
            while (true){
                Socket acceptedClientSocket = serverSocket.accept();

                // Using the Thread Pool to handle the client
                server.threadPool.execute(() -> server.handleClient(acceptedClientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.threadPool.shutdown();
        }

    }
}
