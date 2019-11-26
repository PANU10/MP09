package com.company.UF1.Segundo_Ejercicio;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Codigo {

    private KeyPair keys;

    public static KeyPair randomGenerate(int len) {
        KeyPair keys = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(len);
            keys = keyGen.genKeyPair();
        } catch (Exception ex) {
            System.err.println("Generador no disponible.");
        }
        return keys;
    }

    public byte[] encryptData(byte[] data, PublicKey pub) {
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
            cipher.init(Cipher.ENCRYPT_MODE, pub);
            encryptedData = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error xifrant: " + ex);
        }
        return encryptedData;
    }

    public byte[] dencryptData(byte[] data, PrivateKey piv) {
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
            cipher.init(Cipher.DECRYPT_MODE, piv);
            encryptedData = cipher.doFinal(data);
        } catch (Exception ex) {
            System.err.println("Error xifrant: " + ex);
        }
        return encryptedData;
    }

    public KeyStore loadKeyStore(String ksFile, String ksPwd) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12");
        File f = new File(ksFile);
        if (f.isFile()) {
            FileInputStream in = new FileInputStream(f);
            ks.load(in, ksPwd.toCharArray());
        }
        return ks;
    }

    public SecretKey keygenKeyGeneration(int keySize) {
        SecretKey sKey = null;
        if ((keySize == 128) || (keySize == 192) || (keySize == 256)) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(keySize);
                sKey = kgen.generateKey();

            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Generador no disponible.");
            }
        }
        return sKey;
    }

    public PublicKey getPublic(){
        return keys.getPublic();
    }

    public PrivateKey getPrivate(){
        return keys.getPrivate();
    }

    public PublicKey getPublicKey(String fitxer) throws CertificateException, IOException {
        PublicKey publicKey;
        FileInputStream fis = new FileInputStream(fitxer);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cer = (X509Certificate) cf.generateCertificate(fis);
        return cer.getPublicKey();
    }

    public PublicKey getPublicKey(KeyStore ks, String alias, String pwMyKey) throws KeyStoreException {
        return ks.getCertificate(alias).getPublicKey();
    }


    public static byte[] signData(byte[] data, PrivateKey priv) {
        byte[] signature = null;

        try {
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initSign(priv);
            signer.update(data);
            signature = signer.sign();
        } catch (Exception ex) {
            System.err.println("Error signant les dades: " + ex);
        }
        return signature;
    }

    public static boolean validateSignature(byte[] data, byte[] signature, PublicKey pub) {
        boolean isValid = false;
        try {
            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initVerify(pub);
            signer.update(data);
            isValid = signer.verify(signature);
        } catch (Exception ex) {
            System.err.println("Error validant les dades: " + ex);
        }
        return isValid;
    }


    public static byte[][] encryptWrappedData(byte[] data, PublicKey pub) {
        byte[][] encWrappedData = new byte[2][];
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);// el tamaño de la clave
            SecretKey sKey = kgen.generateKey(); // Genera una SecretKey
            Cipher cipher = Cipher.getInstance("AES"); // Define el algoritmo de cifrado del cipher
            cipher.init(Cipher.ENCRYPT_MODE, sKey); // Inici el encrypt
            byte[] encMsg = cipher.doFinal(data); // Encripta los datos
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // Define el algoritmo de la clave publica
            cipher.init(Cipher.WRAP_MODE, pub); // Inicia el wrap de la clave pública
            byte[] encKey = cipher.wrap(sKey); // Hace un wrap de la SecretKey de la clave pública
            encWrappedData[0] = encMsg; // Guarda los datos encriptados
            encWrappedData[1] = encKey; // Guarda la clave encriptada
        } catch (Exception  ex) {
            System.err.println("Ha succeït un error xifrant: " + ex);
        }
        return encWrappedData;
    }

    public static byte[] decryptWrappedData(byte[][] data, PrivateKey pub) {
        byte[] encMsg = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.UNWRAP_MODE, pub); // Inicia el unwrap de la clave privada
            Key skey = cipher.unwrap(data[1],"AES",Cipher.SECRET_KEY); // Obtiene la clave privada
            cipher = Cipher.getInstance("AES"); // Define el Algoritmo de los datos
            cipher.init(Cipher.DECRYPT_MODE, skey); // Inicia el decrypt de los datos usando la private key que hemos obtenido
            encMsg = cipher.doFinal(data[0]); // Desencripta los datos
        } catch (Exception  ex) {
            System.err.println("Ha succeït un error desxifrant: " + ex);
        }
        return encMsg;
    }

}

