package br.com.nomad.thiago.controleautomotivo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by thiagoj on 14/10/14.
 */
public class Sha1 {

    public static String sha1(String mensagem) {
        try {
            MessageDigest m = MessageDigest.getInstance("SHA1");
            byte[] result = m.digest(mensagem.getBytes());
            StringBuffer SB = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                SB.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            return SB.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

}
