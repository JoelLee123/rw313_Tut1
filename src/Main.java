import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        // This is the port that the server will listen on.
        final int serverPort = 5000;

        // Create a new Server instance.
        Server server = new Server(serverPort);

        // Start the Server in a new thread.
        new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Wait for the server to start up.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create and connect two clients for testing.
        // In a real-world scenario, the clients would be on different machines
        // and would be started independently from the server.

        Client client1 = new Client("localhost", serverPort);
        Client client2 = new Client("localhost", serverPort);

        // Connect the clients to the server.
        try {
            client1.connect();
            client2.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // The clients can now send and receive messages.
        // You would typically have a GUI or a command-line interface for this.
        client1.sendMessage("Hello from client 1!");
        client2.sendMessage("Hello from client 2!");
    }
}
