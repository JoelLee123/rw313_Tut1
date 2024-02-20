import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Server {//
 
    private ServerSocket serverSocket;//

    public Server(ServerSocket serverSocket) {//
        this.serverSocket = serverSocket;//
    }//

    /*
     * A thread is a sequence of instructions within a program that
     * can be executed independently of other code.
     * Threads share a memory space.
     * When you launch an executable, it is running in a thread
     * within a process.
     */
    public void startServer() {//
        
        try {
            //First while loop is so that the server waits on the clients
            while (!serverSocket.isClosed()) {//

                Socket socket = serverSocket.accept();//
                System.out.println("A new client has connected!");//
                ClientHandler clientHandler = new ClientHandler(socket);//
            
                /*
                 * Spawning more threads is vital for allowing multiple clients
                 */
                Thread thread = new Thread(clientHandler);//
                thread.start();//
            }

        } catch (IOException e) {//
            // TODO: handle exception
        }
    }

    /*
     * A computer program becomes a process when it is loaded
     * from some store in the computer's memory and begins execution.
     * A process can be executed by a processor or a set of processors.
     * 
     * A process description contains information such as the program
     * counter (which instruction is currently being executed);
     * registers, variable stores, file handlers, signals, etc.
     */
    public void closeServerSocket() {//
        try {//
            if (serverSocket != null) {//
                serverSocket.close();//
            }//
        } catch (IOException e) {//
            // TODO: handle exception
            e.printStackTrace();//
        }
    }

    public static void main(String[] args) throws IOException {//
        
        //1234 is an assigned port number
        ServerSocket serverSocket = new ServerSocket(1234);//
        Server server = new Server(serverSocket);//
        server.startServer();//
    }
 }