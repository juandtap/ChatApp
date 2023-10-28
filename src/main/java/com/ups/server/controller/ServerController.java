package com.ups.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private final int PORT = 4321;
    
    private ServerSocket serverSocket = null;
    private Socket clientsSocket = null;
    private List<PrintWriter> clientWriters = new ArrayList<>();


    public void startServer(){
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado en el puerto :"+PORT);
            while(true){
                System.out.println("Esperando clientes");
                clientsSocket = serverSocket.accept();
                System.out.println("Nuevo cliente connectado ");
                PrintWriter pout = new PrintWriter(clientsSocket.getOutputStream(), true);
                clientWriters.add(pout);
                
                new Thread(new ChatClientHandler(clientsSocket,this,pout)).start();

            }
        } catch (IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void broadcastMessage(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }

}
