/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ups.client.controller;

import com.ups.cifradoAES.CifradoAES;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
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
                
                String mensajeRecibidoCifrado = in.readLine();
                // decicfrar el mensaje
                String mensajeDecifrado = CifradoAES.decrypt(Base64.getDecoder().decode(mensajeRecibidoCifrado),CifradoAES.getSecretKey());
                if (mensajeRecibidoCifrado == null) {
                    break;
                }
                System.out.println("mensage decifrado "+mensajeDecifrado);
                chatArea.append(mensajeDecifrado + "\n");
            }

        } catch (IOException e) {
            System.out.println("Error en conexion al servidor: "+e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("error cerrando el socket: "+e.getMessage());
            }
        }

    }
    
}
