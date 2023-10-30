/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ups.server;

import com.ups.server.controller.ServerController;
import com.ups.server.view.ServerChat;
import java.io.IOException;

/**
 *
 * @author juand
 */
public class MainServer {
    public static void main(String[] args) throws IOException {
        var chatServer = new ServerChat();
        chatServer.setVisible(true);
    }
}
