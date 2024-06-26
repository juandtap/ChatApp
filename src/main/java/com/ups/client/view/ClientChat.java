/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.ups.client.view;
import com.ups.cifradoAES.CifradoAES;
import com.ups.client.controller.ClientConnectThread;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author ESTUDIANTE
 */
public class ClientChat extends javax.swing.JFrame {

    private static final String SERVER_ADDRESS = "localhost" ;
    private static final int SERVER_PORT = 4321;

    private PrintWriter pout;
    private final String username;
    private static Socket clientSocket;

    private final int NUM_MAX_RECONNECTIONS = 5;

    public ClientChat(String username) throws IOException {
        this.username = username;
        this.setResizable(false);
        initComponents();
        
        this.labelCliente.setText(this.username);

        this.connectToServer();
    }

    private void connectToServer() throws IOException{

        clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        System.out.println("se establece conexion con el servidor: "+clientSocket.toString());
        var threadConnection = new ClientConnectThread(clientSocket, chatArea, this);
        threadConnection.start();
        System.out.println("Iniciado nuevo hilo de escucha: "+threadConnection.getName());
    }
   
    
    public void reconnectToServer() {

        int numIntento = 0;

        boolean socketFlag = false;

            for (int i = 0; i < NUM_MAX_RECONNECTIONS; i++) {


                try {

                    this.connectToServer();
                    socketFlag = true;
                    System.out.println("Conexion restablecida!");
                    this.chatArea.append("Conexion Restablecida! \n");
                    this.labelStatus.setText("Conectado");

                    break;

                } catch (IOException  e) {
                    System.out.println("Error en conexion al servidor: "+e.getMessage());
                    System.out.println("Conexion perdida");
                    this.labelStatus.setText("Desconectado");
                    this.chatArea.append("Conexión perdida...\n");

                    System.out.println("intento numero "+(numIntento++));
                    this.chatArea.append("Reintentando Conexión, intento: "+(numIntento)+"\n");


                }
                // espera 5 segundos antes de reintentar la conexion
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (!socketFlag){
                System.out.println("Numero maximo de intentos de reconexion alcanzado");
                this.chatArea.append("""
                Numero maximo de intentos de reconexion alcanzado
                Conexión terminada!
                """);
                System.out.println("Conexion terminada\n"+"cerrando socket...");
                try {
                    clientSocket.close();
                    System.out.println("SOCKET CERRADO");

                } catch (IOException e) {
                    System.out.println("ERROR CERRANDO SOCKET: "+e.getMessage());
                }
            }
    }

    private void sendMessage() throws IOException {

        pout = new PrintWriter(clientSocket.getOutputStream(),true);
        System.out.println("INFO Socket ENVIO: "+clientSocket.toString());
        String message = messageField.getText();
        if (!message.isEmpty()) {
            
            message = this.username+" : "+message;
            
            if (pout != null) {
                // Envía el mensaje Cifrado al servidor
                try {
                    byte[] mensajeCifrado = CifradoAES.encrypt(message,CifradoAES.getSecretKey());
                    pout.println(Base64.getEncoder().encodeToString(mensajeCifrado));
                    System.out.println("mensaje : <"+message+"> Enviado");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } else {
                System.out.println("Se ha perdido la conexión al servidor.");
            }
            messageField.setText("");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelStatus = new javax.swing.JLabel();
        labelCliente = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        messageField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        labelStatus.setFont(new java.awt.Font("Rockwell", 1, 12)); // NOI18N
        labelStatus.setText("Conectado");

        labelCliente.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        labelCliente.setText("Usuario Activo");

        chatArea.setEditable(false);
        chatArea.setColumns(20);
        chatArea.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        sendButton.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        sendButton.setText("Enviar");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    sendButtonActionPerformed(evt);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        messageField.setText(" ");
        messageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageFieldActionPerformed(evt);
            }
        });
        messageField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                messageFieldKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(sendButton)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(labelCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCliente)
                    .addComponent(labelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
            // Verifica si el campo de mensaje está en blanco
    String message = messageField.getText().trim(); // Quita espacios en blanco al inicio y al final
    if (message.isEmpty()) {
        JOptionPane.showMessageDialog(this, "El campo del mensaje está en blanco.", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        this.sendMessage();
    }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void messageFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_messageFieldActionPerformed

    private void messageFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageFieldKeyPressed
        // TODO add your handling code here:
                
if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
    if (messageField.getText().equals("")) {
        JOptionPane.showMessageDialog(this, "Mensaje en Blanco", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        try {
            sendButtonActionPerformed(null); // Llama al método btnLoginActionPerformed
        } catch (Exception ex) {
            Logger.getLogger(ClientChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

        
        
    }//GEN-LAST:event_messageFieldKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ClientChat().setVisible(true);
  
            }
        });
        
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatArea;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JTextField messageField;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
