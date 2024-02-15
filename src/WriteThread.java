import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private Client client;

    public WriteThread(Socket socket, Client client) {
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        Scanner console = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String userName = console.nextLine();
        client.setUserName(userName);
        writer.println(userName);

        String text;

        do {
            text = console.nextLine();
            if (text.isEmpty()) {
                System.out.println("Message cannot be empty. Please enter your message again.");
                continue;
            }
            if (text.length() > 1000) {
                System.out.println("Message is too long. It should be less than 1000 characters.");
                continue;
            }
            if (text.startsWith("/p ")) {
                // private message
                text = "PRIVATE" + text.substring(2);
            }
            writer.println(text);

        } while (!text.equals("bye"));

        try {
            console.close();
            writer.close();
        } catch (Exception ex) {
            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}
