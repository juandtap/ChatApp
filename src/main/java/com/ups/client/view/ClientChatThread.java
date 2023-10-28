/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ups.client.view;

/**
 *
 * @author juand
 */
public class ClientChatThread extends Thread{
    
    private String username;

    public ClientChatThread(String username) {
        this.username = username;
    }
    
    
    
    @Override
    public void run() {
       var clientChat = new ClientChat(username);
       clientChat.setVisible(true);
    }

    
    
}
