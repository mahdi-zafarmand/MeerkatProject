/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.meerkat;

import config.MeerkatSystem;
import java.io.BufferedReader;
import java.io.File;
import java.net.*;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author aabnar
 */
public class Authentication {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        
        boolean result = getAuthentication("afraabnar@gmail.com", "40e1-99b6");
        System.out.println(result);
    }
    
    /**
     *
     * @param pstrEmailAdd
     * @param pstrSerialNumber
     * @return
     */
    public static boolean getAuthentication(String pstrEmailAdd, String pstrSerialNumber) {
        File flAuth = new File(MeerkatSystem.AUTHENTICATION_FILE);
        
        if (flAuth.exists()) {
            return true;
        } else {
            try {
                
                URL auth = new URL("http://meerkat-srv.aicml.ca/meerkat/"
                        + "client_registration/authenticate.php?email="
                        +pstrEmailAdd
                        +"&serial="
                        +pstrSerialNumber);
                URLConnection con = auth.openConnection();
                BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                con.getInputStream()));
                
                String inputLine = in.readLine();
                
                if (inputLine.trim().equals("TRUE")) {
                    flAuth.createNewFile();
                    return true;
                } else if (inputLine.trim().equals("FALSE")) {
                    return false;
                } else if (inputLine.trim().equals("WRONG EMAIL")) {
                    if (flAuth.exists()) {
                        flAuth.delete();
                    }
                    System.err.println("Wrong Email");
                    return false;
                }
                
        
                in.close();
            } catch (IOException ex) {
                // TODO
            }
        }
        return false;
    }

    /**
     *
     * @param pstrEmailAdd
     * @param pstrSerialNumber
     * @return
     */
    public static boolean reAuthentication(String pstrEmailAdd, String pstrSerialNumber) {
        File flAuth = new File(MeerkatSystem.AUTHENTICATION_FILE);
        
        try {

            URL auth = new URL("http://meerkat-srv.aicml.ca/meerkat/"
                    + "client_registration/authenticate.php?email="
                    +pstrEmailAdd
                    +"&serial="
                    +pstrSerialNumber);
            URLConnection con = auth.openConnection();
            BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                            con.getInputStream()));

            String inputLine = in.readLine();

            if (inputLine.trim().equals("TRUE")) {
                if (!flAuth.exists()) {
                    flAuth.createNewFile();
                }
                return true;
            } else if (inputLine.trim().equals("FALSE")) {
                if (flAuth.exists()) {
                    flAuth.delete();
                }
                return false;
            } else if (inputLine.trim().equals("WRONG EMAIL")) {
                if (flAuth.exists()) {
                    flAuth.delete();
                }
                System.err.println("Wrong Email");
                return false;
            }

            in.close();
        } catch (IOException ex) {
            // TODO
        }

        return false;
    }
}
