package com.danilojakob.m223.punchclock.util;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;

@Component
public class Encryption {

    public String sha256(String toHash) {
        String hashed = "";
        
        try {
            //Hash algorithm
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] md = messageDigest.digest(toHash.getBytes());
            BigInteger no = new BigInteger(1, md);
            hashed = no.toString(16);

            while(hashed.length() < 32) {
                hashed = "0" + hashed;
            }
        } catch(Exception e) {
            return "error";
        }
        return hashed;
    }
}
