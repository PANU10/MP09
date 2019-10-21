package com.company;

import javax.crypto.SecretKey;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String usuario = "Hola Mundo¡";
            Codigo_clave codigo_clave = new Codigo_clave();

            codigo_clave.keygenKeyGeneration(256);
            codigo_clave.passwordKeyGeneration("pratik", 256);
            codigo_clave.encryptData();


    }
}
