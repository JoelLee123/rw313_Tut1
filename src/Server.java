import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
    private boolean isRunning = true;

    public Server(int port) {
        this.port = port;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Chat Server is listening on port " + port);

            while (isRunning) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("New user connected");

                    UserThread newUser = new UserThread(socket, this);
                    userThreads.add(newUser);
                    newUser.start();
                } catch (IOException ex) {
                    System.out.println("Error accepting new connection: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }

        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    void sendPrivateMessage(String message, String recipientName, UserThread sender) {
        for (UserThread aUser : userThreads) {
            if (aUser.getUserName().equals(recipientName)) {
                aUser.sendMessage(message);
                return;
            }
        }
        sender.sendMessage("User " + recipientName + " was not found.");
    }

    void addUserName(String userName) {
        userNames.add(userName);
    }

    void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("User " + userName + " quit");
        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }

    void stop() {
        isRunning = false;
    }
}
