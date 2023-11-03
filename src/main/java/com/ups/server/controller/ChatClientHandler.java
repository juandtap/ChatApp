package com.ups.server.controller;

import com.ups.cifradoAES.CifradoAES;
import com.ups.utils.DataManager;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;

public class ChatClientHandler implements Runnable {


    public static ArrayList<ChatClientHandler> clientHandlers  = new ArrayList<>();

    public Socket clientSocket;

    private PrintWriter pout;
    private JTextArea chatArea;

    public ChatClientHandler(Socket clientSocket,JTextArea chatArea) throws IOException {

        this.chatArea = chatArea;
        try {
            this.clientSocket = clientSocket;
            this.pout = new PrintWriter(clientSocket.getOutputStream(), true);

            clientHandlers.add(this);

        } catch (IOException e) {
            System.out.println("ERROR conexion cliente: "+e.getMessage());
            this.clientSocket.close();
        }

    }


    @Override
    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String mensajeCifradoRecibido;
            while ((mensajeCifradoRecibido = in.readLine())!= null){
                // recibe el mensaje cifrado
                System.out.println("Mensage recibido: "+mensajeCifradoRecibido);
                broadcastMessage(mensajeCifradoRecibido);
                this.chatArea.append(mensajeCifradoRecibido+"\n");
            }
        }catch (IOException e){
            System.out.println("Error: "+e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            }catch (IOException e){
                System.out.println("Error cerrando socket: "+e.getMessage());
            }
        }
    }

    public void broadcastMessage(String message){
        for (ChatClientHandler client: clientHandlers) {
            try {
                client.pout.println(message);
            }catch (Exception e){
                System.out.println("Error en broadcast: "+e.getMessage());
            }
        }
    }
}
