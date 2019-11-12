package com.company.Segundo;

import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.sql.SQLOutput;
import java.util.Enumeration;

public class Main_Segundo {

    public static void main(String[] args) throws Exception {
        ejercicio1(new Codigo());
        ejercicio2(new Codigo());
    }

    public static void ejercicio1 (Codigo codigo){
        System.out.println("Ejercicio 1: ");
        System.out.println("*******************************");
        String texto = "Hola Mundo!";
        System.out.println("Encrypt data : ");
        KeyPair key = codigo.randomGenerate(1024);
        byte [] xif = codigo.encryptData(texto.getBytes(), key.getPublic());
        System.out.println(new String(xif));

        System.out.println("Dencrypt data : ");
          byte [] dexif = codigo.dencryptData(xif, key.getPrivate());
        System.out.println(new String(dexif));
    }


    public static void ejercicio2 (Codigo codigo) throws Exception {
        System.out.println("Ejercicio 2 : ");
        System.out.println("*******************************");
        String password = "usuario";
        String file = "/home/dam2a/keystore_Pratik.key";
        KeyStore store = codigo.loadKeyStore(file,password);
        System.out.println(store.getType());
        System.out.println(store.size());
        Enumeration<String> alias = store.aliases();
        while (alias.hasMoreElements()) {
            String s = alias.nextElement();
            System.out.println(s);
        }
        System.out.println(store.aliases().toString());
        System.out.println(store.getCertificate("mykey"));
        System.out.println(store.getKey("mykey", password.toCharArray()).getAlgorithm());
        System.out.println("Ejercicio: 2.1");
        System.out.println("********************************");
        SecretKey key = codigo.keygenKeyGeneration(256);
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(password.toCharArray());
        KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(key);
        store.setEntry("secretKeyAlias", skEntry, protParam);
        try (FileOutputStream fos = new FileOutputStream("/home/dam2a/Escriptori/2.1.keytore")) {
            store.store(fos, password.toCharArray());
        }
        System.out.println(store.getEntry("secretKeyAlias", protParam));
    }



}
