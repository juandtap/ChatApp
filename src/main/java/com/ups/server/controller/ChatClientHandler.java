package com.ups.server.controller;

import com.ups.cifradoAES.CifradoAES;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

public class ChatClientHandler implements Runnable{

    private Socket clientSocket;
    private ServerController server;
    private PrintWriter pout;

    public ChatClientHandler(Socket clientSocket, ServerController server, PrintWriter pout){
        this.clientSocket = clientSocket;
        this.server = server;
        this.pout = pout;
    }

    public  void  sendMessage(String message){
        System.out.println(message);
    }

    @Override
    public void run() {
        try {
            pout = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String mensajeRecividoCifrado;
            while ((mensajeRecividoCifrado = in.readLine())!= null){
                // recibe el mensaje cifrado
                System.out.println("Mensage recibido: "+mensajeRecividoCifrado);
                // decifrar el mensaje
                ///String mensajeDecifrado = CifradoAES.decrypt(Base64.getDecoder().decode(mensajeRecividoCifrado), CifradoAES.getSecretKey());
                // envio por broadcast el mensaje cifrado
                this.server.broadcastMessage(mensajeRecividoCifrado);
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
}
