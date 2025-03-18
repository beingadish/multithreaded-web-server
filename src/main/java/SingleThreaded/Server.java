package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void run() throws IOException{
        int port = 8010;
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(100000);
            while(true){
                System.out.println("waiting for client");
                Socket acceptedConnection = serverSocket.accept();
                System.out.println("Connection accepted from " + acceptedConnection.getRemoteSocketAddress() + " on port " + acceptedConnection.getPort());
                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
                toClient.println("HTTP/1.1 200 OK");
                fromClient.readLine();
                acceptedConnection.close();
                toClient.close();
                fromClient.close();
                System.out.println("Connection closed");
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