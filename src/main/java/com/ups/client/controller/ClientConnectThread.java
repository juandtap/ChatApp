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
import javax.swing.*;

/**
 *
 * @author juand
 */
public class ClientConnectThread extends Thread{

    
    
    // hilo para la ejecucion del proceseo de escucha al servidor
    
    private JTextArea chatArea;
    private JLabel labelConnStatus;
   
    private Socket clientSocket;
   
    private BufferedReader in;

    private final int NUMMAXRECONNECT = 5;
    private int numIntento = 0;

    private  String serverAddress;
    private int serverPort;
    private PrintWriter pout;

    private boolean isConnectedFlag = true;

    public ClientConnectThread(Socket socket, JTextArea chatArea, JLabel labelConnStatus, String serverAddress, int serverPort, PrintWriter pout) {
        this.clientSocket = socket;
        this.chatArea = chatArea;
        this.labelConnStatus = labelConnStatus;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.pout = pout;
    }
    
    
    
    @Override
    public void run() {



        for (int i = 0; i < NUMMAXRECONNECT; i++) {
            System.out.println("Conexion establecida numero: "+(i+1));
            this.connectToServer();
            System.out.println("estado del socket: "+this.clientSocket.isConnected());
            // espera 5 segundos antes de reintentar conexion
            System.out.println("Espera 5 segundos antes de reintentar nuevamente");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Numero maximo de intentos de reconexion alcanzado");
        this.chatArea.append("""
                Numero maximo de intentos de reconexion alcanzado
                Conexión terminada!
                """);
        System.out.println("Conexion terminada\n"+"cerrando socket...");
        try {
            this.clientSocket.close();
            System.out.println("socket cerrado");

        } catch (IOException e) {
            System.out.println("ERROR CERRANDO SOCKET: "+e.getMessage());
        }

    }
    

     public void connectToServer() {

        try {
            

            if (!isConnectedFlag){
                // se vuelve a instanciar el socket
                this.clientSocket = new Socket(serverAddress, serverPort);
                // se vuelve a instaciar el PrintWriter, para que puede volver a enviar los mensajes
                this.pout = new PrintWriter(this.clientSocket.getOutputStream(), true);
                isConnectedFlag = true;
                System.out.println("Conexion restablecida!");
                this.chatArea.append("Conexion restablecida!\n");
                this.labelConnStatus.setText("Conectado");
            }

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           

            while (true) {

              System.out.println("estado del socket: "+this.clientSocket.isConnected());

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
            isConnectedFlag = false;

            System.out.println("Error en conexion al servidor: "+e.getMessage());
            System.out.println("Conexion perdida");
            this.labelConnStatus.setText("Desconectado");
            this.chatArea.append("Conexión perdida...\n");

            System.out.println("intento nmero "+(numIntento++));
            this.chatArea.append("Reintentando Conexión, intento: "+(numIntento)+"\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    
}
