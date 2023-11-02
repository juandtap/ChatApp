package com.ups.server.controller;

import com.ups.cifradoAES.CifradoAES;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;

public class ChatClientHandler implements Runnable{

    public static ArrayList<ChatClientHandler> clientHandlers = new ArrayList<>();
    public Socket clientSocket;
    //private ServerController server;
    private PrintWriter pout;

    public ChatClientHandler(Socket clientSocket) throws IOException {


        try {
            this.clientSocket = clientSocket;
            this.pout = new PrintWriter(clientSocket.getOutputStream(), true);
            clientHandlers.add(this);
        } catch (IOException e) {
            System.out.println("ERROR conexion cliente: "+e.getMessage());
            this.clientSocket.close();
        }

    }

    public  void  sendMessage(String message){
        System.out.println(message);
    }

    @Override
    public void run() {
        try {
            //pout = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String mensajeCifradoRecibido;
            while ((mensajeCifradoRecibido = in.readLine())!= null){
                // recibe el mensaje cifrado
                System.out.println("Mensage recibido: "+mensajeCifradoRecibido);
                // decifrar el mensaje
                ///String mensajeDecifrado = CifradoAES.decrypt(Base64.getDecoder().decode(mensajeCifradoRecibido), CifradoAES.getSecretKey());
                // envio por broadcast el mensaje cifrado
                broadcastMessage(mensajeCifradoRecibido);
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
