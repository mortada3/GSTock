package com.example.gstock;

import java.security.MessageDigest;

public class SharedHelper {
    public static String sha256(String s ){
        try {
            // creation du hash SHA256
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA256");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            //Création de la chaîne en hexa
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++){
                hexString.append(Integer.toHexString( messageDigest[i] & 0xFF));
            }
            return hexString.toString();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return "";
    }
}
