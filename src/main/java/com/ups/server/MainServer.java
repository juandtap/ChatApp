/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ups.server;

import com.ups.server.controller.ServerController;

/**
 *
 * @author juand
 */
public class MainServer {
    public static void main(String[] args) {
        var chatServer = new ServerController();
        chatServer.startServer();
    }
}
