package com.company.UF1.Segundo_Ejercicio;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Enumeration;

public class Main_Segundo {

    public static void main(String[] args) throws Exception {
        ejercicio1(new Codigo());
        ejercicio2(new Codigo());
        ejercicio3(new Codigo());
        ejercicio4(new Codigo());
        ejercicio5_6_Ultimo(new Codigo());
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
        System.out.println("Tipo de keytore :" +store.getType());
        System.out.println("Numero de claves :" +store.size());
        Enumeration<String> alias = store.aliases();
        while (alias.hasMoreElements()) {
            String s = alias.nextElement();
            System.out.println(s);
        }
        System.out.println("Alias : " + store.aliases().toString());
        System.out.println("Certificado :"+ store.getCertificate("mykey"));
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

    public static void ejercicio3 (Codigo codigo) throws Exception{
        System.out.println("Ejercicio 3");
        System.out.println(codigo.getPublicKey("/home/dam2a/pratik.cer"));
    }

    public static void ejercicio4 (Codigo codigo) throws Exception {
        System.out.println("Ejercicio 4");
        KeyPair key = codigo.randomGenerate(1024);
        String password = "usuario";
        String file = "/home/dam2a/keystore_Pratik.key";
        KeyStore store = codigo.loadKeyStore(file,password);
        PublicKey mykey = codigo.getPublicKey(store, "mykey", password);
        System.out.println(mykey);
    }

    public static void ejercicio5_6_Ultimo (Codigo codigo) {

        KeyPair key = codigo.randomGenerate(1024);

        byte[] dataBy = "Hola mundo".getBytes();

        PrivateKey privKey = key.getPrivate();

        byte[] firma = Codigo.signData(dataBy,privKey);

        System.out.println(new String(firma));

        System.out.println("Ejercicio 6");

        PublicKey publicKey = key.getPublic();

        boolean verificado = Codigo.validateSignature(dataBy,firma,publicKey);

        System.out.println(verificado);

        System.out.println("Ultimo Ejercicio");

        KeyPair claves = Codigo.randomGenerate(1024);

        PublicKey pubKey = claves.getPublic();
        PrivateKey privateKey = claves.getPrivate();

        byte[][] clauEmbEnc = Codigo.encryptWrappedData(dataBy,pubKey);

        byte[]  clauEmbDec = Codigo.decryptWrappedData(clauEmbEnc,privateKey);

        System.out.println(new String(clauEmbDec));
    }
}
