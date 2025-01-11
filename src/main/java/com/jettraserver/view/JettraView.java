/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jettraserver.view;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author avbravo
 */
public interface JettraView {

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_HTML)
    public Response draw();

    public default Response generate(String path) {
        try {

            String text = "";
            URL resource = getClass().getClassLoader().getResource(path);
            if (resource == null) {
                text = "<h1>"+ path+" no encontrado</h1>";
                //throw new IllegalArgumentException("file not found!");
            } else {

                try {
                    var file1 = new File(resource.toURI());
                    Scanner myReader = new Scanner(file1);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        text += data;
                     //   System.out.println(data);
                    }

                } catch (URISyntaxException ex) {
                    return Response.ok("<h2> Error <p>" + ex.getLocalizedMessage() + "</p></h2>").build();
                } catch (FileNotFoundException ex) {
                    return Response.ok("<h2> Error <p>" + ex.getLocalizedMessage() + "</p></h2>").build();
                }
            }

            var html = """
                   
                       """;
            html = text;
            return Response.ok(html).build();
        } catch (Exception e) {
            return Response.ok("<h2> Error <p>" + e.getLocalizedMessage() + "</p></h2>").build();
        }
    }
}
