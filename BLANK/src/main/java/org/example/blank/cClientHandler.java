package org.example.blank;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

//NB THERE IS NO MAIN METHOD IN THIS CLASS
//This is because thread.start() will run the ClientHandler class

public class cClientHandler implements Runnable {

    //The main purpose of this ArrayList is to keep track of all of our clients
    public static ArrayList<cClientHandler> clientHandlers = new ArrayList<cClientHandler>();

    private Socket socket;
    //Used to read messages sent by the client
    //In our case, the client messages will come from the textfield
    private BufferedReader bufferedReader;
    //BufferedWriter will be used to send messages to the client
    //from other clients, these will be broadcast using the clientHandlers arraylist
    private BufferedWriter bufferedWriter;

    private String clientUsername;

    //ASK AI HOW THIS CAN BE CHANGED BECAUSE WE ARE READING FROM JAVAFX ELEMENTS
    //NOT FROM THE TERMINAL
    public cClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat");
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        //Everything in this run method is run on a different thread
        //We want a separate thread that waits for messages
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                //WHEN THE CLIENT DISCONNECTS
                break;
            }
        }
    }

    /*
        We have an array list of clientHandlers
        We can loop through this to send a message to each client connected
     */
    public void broadcastMessage(String messageToSend) {

        for (cClientHandler clientHandler: clientHandlers) {

            try {
                //We want to broadcast the message to everyone except the user that sent it
                //BUT I CAN CHANGE THIS SO THAT EVERYONE SEES THE SENT MESSAGE
                //WITH THE GUI WE WON'T BE USING BUFFERED READER/WRITER
                //SO WE NEED TO PASS IN SCENECONTROLLER SO THAT WE CAN INTERACT WITH THE ELEMENTS
                if (clientHandler.clientUsername.equals(clientUsername)) {
                    //WRITE TO HBOX IN SCENE 2
                    clientHandler.bufferedWriter.write(messageToSend + " (YOU)");
                } else {
                    clientHandler.bufferedWriter.write(messageToSend);
                }
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
            } catch (Exception e) {
                System.out.println("A client has disconnected");
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void removeClientHandler() {
        //Removing the current client handler
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat");
    }

    public String get_username() {
        return clientUsername;
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            //Closing the socket will close the input and output streams
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
