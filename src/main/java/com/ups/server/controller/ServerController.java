package com.ups.server.controller;

import com.ups.utils.DataManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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

    private JTextArea chatArea;


    public ServerController(JTextArea chatArea) {
        this.chatArea = chatArea;

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
                System.out.println("Nuevo cliente conectado ");
                this.chatArea.append("Nuevo Cliente conectado\n");
               //PrintWriter pout = new PrintWriter(clientsSocket.getOutputStream(), true);
                ChatClientHandler chatClientHandler = new ChatClientHandler(clientsSocket, chatArea);


                new Thread(chatClientHandler).start();

            }
        } catch (IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

//    public void broadcastMessage(String message) {
//        this.chatArea.append(message+"\n");
//        for (PrintWriter writer : clientWriters) {
//            writer.println(message);
//
//
//        }
//    }


}
