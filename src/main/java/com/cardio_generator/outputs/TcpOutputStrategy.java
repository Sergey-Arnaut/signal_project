package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Strategy for sending simulated patient data over TCP sockets.
 * <p>
 * Starts a TCP server on the specified port and broadcasts data records
 * to a connected client using a PrintWriter.
 */
public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket; // Server socket listening for incoming connections.
    private Socket clientSocket;       // Client socket representing the connected client.
    private PrintWriter out;           // Writer used to send messages to the client.

    /**
     * Constructs a TcpOutputStrategy and starts a server on the given port.
     * Listens for a single client connection in a background thread.
     *
     * @param port the TCP port on which the server listens
     */
    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Outputs a data record to the connected TCP client if present.
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the measurement timestamp in milliseconds since epoch
     * @param label     a short descriptor of the data type (e.g., "ECG", "Alert")
     * @param data      the measurement value or alert detail as a string
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
