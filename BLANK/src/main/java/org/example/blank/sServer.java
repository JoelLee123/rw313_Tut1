package org.example.blank;

import javafx.beans.binding.StringBinding;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class sServer {

    private ServerSocket serverSocket;

    public static ArrayList<String> usernames = new ArrayList<>();
    public sServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    //I chose to pass in Socket so that the username could be displayed when a user leaves
    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                //A blocking method that waits until a client connects
                Socket socket = serverSocket.accept();
                //After this, a client has connected to the server!

                //Each object of this class will be responsible for communicating
                //with a client
                //It will implement Runnable

                cClientHandler clientHandler = new cClientHandler(socket);
                System.out.println(clientHandler.get_username() + " has connected");

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    public static synchronized boolean userNameTaken(String username) {
        if (!usernames.contains(username)) {
            usernames.add(username);
            return false;
        }
        return true;
    }

    public void closeServerSocket() {
        try {
            /*
                Do not change this method!
                Getting exact username is not worth it
             */
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        sServer server = new sServer(serverSocket);
        server.startServer();
    }
}