package org.example.blank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class cClient {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public cClient(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    //This will be used to send messages to our client handler
    public void sendMessage() {
        try {
            //So that the clientHandler knows who we are
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            //ASK AI HOW THIS CAN BE REPLACED SO THAT THE USERNAME IS READ FROM THE TEXTFIELD

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /*
        This will be waiting for messages that are broadcasted
        It will loop through the clientHanlders arraylist in broadCast message
     */
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageGroupChat;

                while (socket.isConnected()) {
                    try {
                        messageGroupChat = bufferedReader.readLine();
                        if (messageGroupChat == null) {
                            System.out.println("SERVER DOWN, DISCONNECTING CLIENTS...");
                            System.exit(0);
                        }
                        System.out.println(messageGroupChat);
                    } catch (IOException e) {
                        System.out.println("BANANA");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

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

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");

        Socket socket = new Socket("localhost", 1234);

        String username = scanner.nextLine();
        while (socket.isConnected()) {
            if (sServer.userNameTaken(username)) {
                System.out.println("USERNAME TAKEN: ENTER A NEW USERNAME:");
                username = scanner.nextLine();
            } else {
                break;
            }
        }


        cClient client = new cClient(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
}
