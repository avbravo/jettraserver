/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jettraserver.config;

import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author avbravo
 */
public interface JettraConfig {

    /**
     * Obtiene propiedad del archivo MicroprofileConfig
     *
     * @param propertie
     * @return
     */
    default String getMicroprofileConfig(String propertie) {
        var result = "";
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("META-INF/microprofile-config.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return result;
            }

            prop.load(input);

            //get the property value and print it out
            result = prop.getProperty(propertie);
            System.out.println(".... result " + result);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
