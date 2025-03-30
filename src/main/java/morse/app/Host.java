package morse.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Host {
    
    private ServerSocket serverSocket;
    String roomName;
    int port;
    String networkName;
    ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private final List<Socket> clients = new CopyOnWriteArrayList<>(); // Thread-safe list to store client sockets
    private final List<String> messages = new CopyOnWriteArrayList<>(); // Thread-safe list to store messages

    public Host(String roomName){
        this.roomName = roomName;
        this.networkName = getHostname();
        if(networkName.equals("Unknown")){
            return;
        }
        try {
            this.serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.port = serverSocket.getLocalPort();
        runServer();
    }

    private String getHostname() {
        String hostname = System.getenv("HOSTNAME"); // Check Unix/Linux environment variable
        if (hostname == null || hostname.isEmpty()) {
            hostname = System.getenv("COMPUTERNAME"); // Check Windows environment variable
        }
        if (hostname == null || hostname.isEmpty()) {
            try {
                hostname = java.net.InetAddress.getLocalHost().getHostName(); // Fallback method
            } catch (java.net.UnknownHostException e) {
                e.printStackTrace();
                hostname = "Unknown";
            }
        }
        return hostname;
    }

    private void runServer(){
        new Thread(() -> {
            try {
                while (true) {
                    // Accept incoming connections and handle them in a separate thread
                    Socket clientSocket = serverSocket.accept();
                    clients.add(clientSocket); // Add the client socket to the list
                    threadPool.submit(new ClientHandler(clientSocket));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void broadcastMessage(String message) {
        messages.add(message); // Add the message to the list
        for (Socket client : clients) {
            try {
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println(message); // Send the message to the client
            } catch (IOException e) {
                System.err.println("Error broadcasting message to client: " + e.getMessage());
                clients.remove(client); // Remove disconnected client
            }
        }
    }


private class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private String clientName;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Example handling client input
            InputStream input = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line;
            line = reader.readLine(); // Read the first line for client name or ID
            if (line != null) {
                this.clientName = line; // Store the client name or ID
                broadcastMessage("Client connected: " + clientName);
            } else {
                this.clientName = "Unknown User"; // Default name if not provided
            }
            // Read and broadcast client messages
            while ((line = reader.readLine()) != null) {
                // Process client data
                broadcastMessage(line);
            }

            // Client disconnected (EOF or null from readLine)
            broadcastMessage(clientName + ": has left the chat");
        } catch (IOException e) {
            // Handle exceptions (e.g., connection closed unexpectedly)
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                // Close the client socket to release resources
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}

}


