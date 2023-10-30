/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ups.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JTextArea;

/**
 *
 * @author juand
 */
public class ClientConnectThread extends Thread{

    
    
    // hilo para la ejecucion del proceseo de escucha al servidor
    
    private JTextArea chatArea;
   
   
    private Socket clientSocket;
   
    private BufferedReader in;

    public ClientConnectThread(Socket socket, JTextArea chatArea) {
        this.clientSocket = socket;
        this.chatArea = chatArea;
    }
    
    
    
    @Override
    public void run() {
       this.connectToServer();
    }
    

     public void connectToServer() {

        try {
            
            
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           

            while (true) {
                
                String message = in.readLine();
               
                if (message == null) {
                    break;
                }
                chatArea.append(message + "\n");
            }

        } catch (IOException e) {
            System.out.println("Error en conexion al servidor: "+e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("error cerrando el socket: "+e.getMessage());
            }
        }

    }
    
}
