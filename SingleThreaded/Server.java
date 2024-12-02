import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void run() throws IOException {
        int port = 8010;

        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(port);

        // Set a timeout for client connections (in milliseconds)
        serverSocket.setSoTimeout(10000);

        System.out.println("Server is listening on port " + port);

        while (true) {
            try {
                // Accept a client connection
                Socket acceptedConnection = serverSocket.accept();
                System.out.println("Connection accepted from client " + acceptedConnection.getRemoteSocketAddress());

                // Create streams for communication
                PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true); // Auto-flush enabled
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
                fromClient.close();

                // Send a message to the client
                toClient.println("Hello from the server");
                toClient.close();

                // Read a message from the client (optional)
                String clientMessage;
                while ((clientMessage = fromClient.readLine()) != null) {
                    System.out.println("Client says: " + clientMessage);

                }

                // Close the connection with the client
                acceptedConnection.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

