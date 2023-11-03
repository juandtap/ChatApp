/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.ups.cifradoAES;

import com.ups.utils.DataManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import javax.crypto.spec.SecretKeySpec;

public class CifradoAES {

    public static void main(String[] args) {
        try {
            
            
            
            //Se genera un calve AES y la gurda en un archivo
            
            // Genera una clave AES
            /* SecretKey secretKey = generateAESKey();
            // Convierte la clave en un formato adecuado para el almacenamiento, por ejemplo, en bytes
            byte[] keyBytes = secretKey.getEncoded();

            // Guardar la clave en un archivo
            try (FileOutputStream fos = new FileOutputStream("clave_aes.key")) {
                fos.write(keyBytes);
            }
            */

            
            
           /* // Mensaje que deseas cifrar
            String mensajeOriginal = "Paul Andres Astudillo Calle.";

            // Cifra el mensaje
            byte[] mensajeCifrado = encrypt(mensajeOriginal, secretKey);

            // Descifra el mensaje
            String mensajeDescifrado = decrypt(mensajeCifrado, secretKey);

            System.out.println("Mensaje original: " + mensajeOriginal);
            System.out.println("Mensaje cifrado: " + Base64.getEncoder().encodeToString(mensajeCifrado));
            System.out.println("Mensaje descifrado: " + mensajeDescifrado);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Genera una clave AES Ya no se usa
//    public static SecretKey generateAESKey() throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128); //Usamos 128 bits para cifrar 
//        return keyGenerator.generateKey();
//    }

    // Cifra un mensaje usando una clave AES
    public static byte[] encrypt(String mensaje, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(mensaje.getBytes(StandardCharsets.UTF_8));
        return encryptedBytes;
        
        //Utilizamos un objeto Cipher configurado en modo de cifrado (ENCRYPT_MODE)
        //y la clave para cifrar el mensaje. El resultado es un array de bytes que representa el mensaje cifrado.
    }

    // Descifra un mensaje usando una clave AES
    public static String decrypt(byte[] encryptedBytes, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
        
        //Utilizamos un objeto Cipher configurado en modo de cifrado (DECRYPT_MODE)
    }
    
    public  static SecretKey getSecretKey() throws FileNotFoundException, IOException{
                // Lee los bytes de la clave desde el archivo
        byte[] keyBytes;
        try (FileInputStream fis = new FileInputStream(DataManager.getDataPath()+"cifradoAES/clave_aes.key")) {
            keyBytes = fis.readAllBytes();
        }

        // Reconstruye la clave a partir de los bytes leídos
        return new SecretKeySpec(keyBytes, "AES");
    } 
}

