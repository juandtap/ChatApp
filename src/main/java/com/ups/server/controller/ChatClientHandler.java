package com.ups.server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientHandler implements Runnable{

    private Socket clientSocket;
    private ServerController server;
    private PrintWriter out;

    public ChatClientHandler(Socket clientSocket, ServerController server){
        this.clientSocket = clientSocket;
        this.server = server;
    }

    public  void  sendMessage(String message){
        System.out.println(message);
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;
            while ((message = in.readLine())!= null){
                System.out.println("Mensage recibido: "+message);
                this.server.broadcastMessage(message);
            }
        }catch (IOException e){
            System.out.println("Error: "+e.getMessage());
        } finally {
            try {
                clientSocket.close();
            }catch (IOException e){
                System.out.println("Error cerrando socket: "+e.getMessage());
            }
        }
    }
}
