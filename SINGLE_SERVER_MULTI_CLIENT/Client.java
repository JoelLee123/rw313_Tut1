import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;

public class Client {
    
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {

        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
        
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);

            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            // TODO: handle exception
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Concurrency (single core) vs Parallel execution (Multi-Core)
     * 
     * Concurrency - running two or more programs in overlapping time 
     * phases.
     *      - At any given time, there is only one process in execution
     * 
     * Parallel execution - tasks performed by a process are broken down into
     * sub-parts and multiple CPUs execute each sub-task at the same time.
     * 
     *      - At any given time, all the processes are being executed.
     *      - Found in systems having multicore processors.
     *                         
     */
    public void listenForMessage() {

        //Thread constructor
        //Use AI to find another way of doing this
        //As I have no idea what this does!
        new Thread(new Runnable() {

            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
         
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username to enter the group chat: ");
        String username = scanner.nextLine();

        //Localhost can be replaced by the IP address that you want to connect to.
        //Here I put local host as I am connecting to my own laptop.
        //1234 is the port number.
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, username);

        /**
         * Both of these methods have while (true) loops.
         * They will continue to execute while the socket is still connected.
         */
        client.listenForMessage();
        client.sendMessage();
    }
}
