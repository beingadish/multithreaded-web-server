package Multithreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public void run() throws UnknownHostException, IOException{
        int port = 8010;
        InetAddress address = InetAddress.getByName("localhost");
        Socket socket = new Socket(address, port);
        socket.setSoTimeout(100000);
        PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer.println("GET / HTTP/1.1");
        String line = fromServer.readLine();
        System.out.println("Response from the Socket : " + line);
        socket.close();
        fromServer.close();
        toServer.close();
        System.out.println("Connection closed");
        System.exit(0);
    }

    public static void main(String[] args){
        try {
            Client client = new Client();
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
