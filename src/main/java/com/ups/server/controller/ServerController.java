package com.ups.server.controller;


import java.io.*;
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
                ChatClientHandler chatClientHandler = new ChatClientHandler(clientsSocket, chatArea);

                new Thread(chatClientHandler).start();

            }
        } catch (IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }



}
