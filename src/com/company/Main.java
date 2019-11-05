package com.company;

import jdk.nashorn.api.tree.WhileLoopTree;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {

    /*
    *@Autor :- Pratik Kumar
    */

    public static void main(String[] args) throws IOException {
	// write your code here
        String usuario = "Hola Mundo¡";
        String pass = "usuario";
        Codigo_clave codigo_clave = new Codigo_clave();

//        // Ejercicio 1
//            //SecretKey sk = codigo_clave.keygenKeyGeneration(256);
//
//            SecretKey sk = codigo_clave.passwordKeyGeneration(pass, 256);
//
//            byte [] xif = codigo_clave.encryptData(sk, usuario.getBytes());
//
//            //codigo_clave.passwordKeyGeneration(pass, 256);
//
//            byte [] dexif = codigo_clave.decryptData(sk, xif);
//
//            System.out.println(new String(dexif));
            // Ejercicio 2
            String file = "/home/dam2a/Baixades/claves.txt";
            String clave;
            byte[] fileencryp = Files.readAllBytes(Paths.get("/home/dam2a/Baixades/textamagat"));
            BufferedReader b = new BufferedReader(new FileReader(new File(file)));

            while ((clave = b.readLine()) != null){
                SecretKey sKey  = codigo_clave.passwordKeyGeneration(clave, 128);
               try {
                   if (codigo_clave.decryptData(sKey, fileencryp) != null){
                       System.out.println(clave);
                       System.out.println(new String(codigo_clave.decryptData(sKey, fileencryp)));
                   }
               }catch (BadPaddingException bad){
                   clave = b.readLine();
               }
            }
    }
}
