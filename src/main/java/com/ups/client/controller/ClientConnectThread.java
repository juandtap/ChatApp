/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ups.client.controller;

import com.ups.cifradoAES.CifradoAES;
import com.ups.client.view.ClientChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.concurrent.BlockingQueue;
import javax.swing.*;

/**
 *
 * @author juand
 */
public class ClientConnectThread extends Thread{

    // hilo para la ejecucion del proceseo de escucha al servidor
    
    private JTextArea chatArea;
    private Socket clientSocket;
    private BufferedReader in;
   // se tiene como argumento para notificar el fin del thread con el metodo reconnectToServer
    private ClientChat clientChat;

    public ClientConnectThread(Socket socket, JTextArea chatArea, ClientChat clientChat) {
        this.clientSocket = socket;
        this.chatArea = chatArea;
        this.clientChat = clientChat;

    }
    
    
    
    @Override
    public void run() {
        this.connectToServer();
    }
    

     public void connectToServer() {

        try {

            in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            while (true) {

                System.out.println("estado del socket: " + this.clientSocket.isConnected());

                String mensajeRecibidoCifrado = in.readLine();
                // descifrar el mensaje
                String mensajeDecifrado = CifradoAES.decrypt(Base64.getDecoder().decode(mensajeRecibidoCifrado), CifradoAES.getSecretKey());
                if (mensajeRecibidoCifrado == null) {
                    break;
                }
                System.out.println("mensage decifrado " + mensajeDecifrado);
                chatArea.append(mensajeDecifrado + "\n");


            }

        } catch (IOException e) {

            System.out.println("THREAD: conexion perdida");

            Thread.currentThread().interrupt();
            System.out.println("THREAD: Envia notificacion para reconectar");
            clientChat.reconnectToServer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
}
