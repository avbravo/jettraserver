/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jettraserver;

import jakarta.ws.rs.SeBootstrap;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author avbravo Se utiliza para gestionar los datos leidos del repositorio
 */
public class JettraServer {

    private String protocol;
    private String host;
    private Integer port;
    private jakarta.ws.rs.core.Application application;

    public JettraServer() {
    }

    public JettraServer(String protocol, String host, Integer port, jakarta.ws.rs.core.Application application) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.application = application;

    }

    public jakarta.ws.rs.core.Application getApplication() {
        return application;
    }

    public void setApplication(jakarta.ws.rs.core.Application application) {
        this.application = application;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public static class Builder {

        private String protocol;
        private String host;
        private Integer port;
        private jakarta.ws.rs.core.Application application;

        public Builder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder application(jakarta.ws.rs.core.Application application) {
            this.application = application;
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(Integer port) {
            this.port = port;
            return this;
        }

        public JettraServer start() {
            try {

                System.out.println(
                        "___________________________________________________________________________");
                System.out.println(
                        "                         JettraServer starting....");

                SeBootstrap.Configuration.Builder configBuilder = SeBootstrap.Configuration.builder();

                configBuilder.property(SeBootstrap.Configuration.PROTOCOL, protocol)
                        .property(SeBootstrap.Configuration.HOST, host)
                        .property(SeBootstrap.Configuration.PORT, port);

                System.out.println(
                        "....Payara Start....");

                SeBootstrap.Instance instance = SeBootstrap.start(application, configBuilder.build()).toCompletableFuture().get();

                UriBuilder uriBuilder = instance.configuration().baseUriBuilder();
                var httpClient = HttpClient.newBuilder().build();
                var httpRequest = HttpRequest.newBuilder()
                        .uri(uriBuilder
                                .path("helloworld").build())
                        .header("Content-Type", "application/json")
                        .GET().build();

                HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                var body = response.body();

            } catch (Exception e) {
            }
//            catch (InterruptedException i) {
//            } catch (ExecutionException e) {
//            } catch (IOException io) {
//            }

            return new JettraServer(protocol, host, port, application);
        }
    }
}
