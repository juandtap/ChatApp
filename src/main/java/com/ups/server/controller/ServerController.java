package com.ups.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JList;
import javax.swing.JTextArea;

public class ServerController extends Thread{
    
    private final int PORT = 4321;
    
    private ServerSocket serverSocket = null;
    private Socket clientsSocket = null;
    private List<PrintWriter> clientWriters = new ArrayList<>();
    private JTextArea chatArea;
    private JList userList;

    public ServerController(JTextArea chatArea, JList userList) {
        this.chatArea = chatArea;
        this.userList = userList;
    }

    @Override
    public void run() {
        startServer();
    }
    
    
    public void startServer(){
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor iniciado en el puerto :"+PORT);
            while(true){
                System.out.println("Esperando clientes");
                this.chatArea.append("Esperando Clientes...\n");
                clientsSocket = serverSocket.accept();
                System.out.println("Nuevo cliente connectado ");
                this.chatArea.append("Nuevo Cliente connectado\n");
                PrintWriter pout = new PrintWriter(clientsSocket.getOutputStream(), true);
                clientWriters.add(pout);
                broadcastMessage("Bienvenido : " +clientWriters.get(clientWriters.size()-1).toString() );
                
                System.out.println("Bienvenido : " +clientWriters.get(clientWriters.size()-1).toString()+"\n");
               
                new Thread(new ChatClientHandler(clientsSocket,this,pout)).start();

            }
        } catch (IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void broadcastMessage(String message) {
        this.chatArea.append(message+"\n");
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
           
                
        }
    }

}
