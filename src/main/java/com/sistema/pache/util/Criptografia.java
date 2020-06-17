/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.pache.util;

/**
 *
 * @author Maik Rabelo
 */

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {
    public static String criptografarSenha(String senha) throws NoSuchAlgorithmException {
        return Criptografia.getMD5Code(senha);
    }

    public static String getMD5Code(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try {
            md.update(text.getBytes());
        }
        catch (NullPointerException e) {
            return null;
        }
        BigInteger hash = new BigInteger(1, md.digest());
        return hash.toString(16);
    }
}
