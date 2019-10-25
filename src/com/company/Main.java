package com.company;

import jdk.nashorn.api.tree.WhileLoopTree;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.Files;

public class Main {

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
            byte[] fileencryp = Files.readAllBytes(new File(file).toPath());
            BufferedReader b = new BufferedReader(new FileReader(new File("/home/dam2a/Baixades/textamagat")));

            while ((clave = b.readLine()) != null){
                SecretKey sKey = codigo_clave.passwordKeyGeneration(clave, 128);
                if (codigo_clave.decryptData(sKey, fileencryp) != null){
                    System.out.println(new String(codigo_clave.decryptData(sKey, fileencryp)));
                }
            }
    }
}
