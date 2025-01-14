/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jettraserver;

import com.jettraserver.enumerations.Protocol;
import com.jettraserver.utils.JettraLogo;
import com.jettraserver.utils.JettraMessage;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.SeBootstrap;
import jakarta.ws.rs.SeBootstrap.Configuration.SSLClientAuthentication;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;

/**
 *
 * @author avbravo Se utiliza para gestionar los datos leidos del repositorio
 */
public class JettraServer {

    /**
     * Runs this example.
     *
     * @param args configuration to be used in exact this order:
     * {@code PROTOCOL HOST PORT ROOT_PATH CLIENT_AUTH} where the protocol can
     * be either { HTTP} or {HTTPS} and the client authentication is one of
     */
    private Protocol protocol = Protocol.HTTP;
    @NotNull(message = "EL rootPath no debe estar vacio")
    private String rootPath;
    //.tls("TLSv1.2")
    private String tls;
    private String host;
    private Integer port;
    private Boolean logo;
    //Ejecuta el test interno del servidor
    @NotNull
    private Boolean runInternalTest = Boolean.FALSE;
    //{NONE, OPTIONAL, MANDATORY}.
    private SSLClientAuthentication sslClientAuthentication;

    private jakarta.ws.rs.core.Application application;

    public JettraServer() {
    }

    public JettraServer(String rootPath, String tls, String host, Integer port, Boolean logo, SSLClientAuthentication sslClientAuthentication, Application application, Boolean runInternalTest) {
        this.rootPath = rootPath;
        this.tls = tls;
        this.host = host;
        this.port = port;
        this.logo = logo;
        this.sslClientAuthentication = sslClientAuthentication;
        this.application = application;
        this.runInternalTest = runInternalTest;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public Boolean getLogo() {
        return logo;
    }

    public void setLogo(Boolean logo) {
        this.logo = logo;
    }

    public Boolean getRunInternalTest() {
        return runInternalTest;
    }

    public void setRunInternalTest(Boolean runInternalTest) {
        this.runInternalTest = runInternalTest;
    }

    
   

    public String getTls() {
        return tls;
    }

    public void setTls(String tls) {
        this.tls = tls;
    }

    public jakarta.ws.rs.core.Application getApplication() {
        return application;
    }

    public void setApplication(jakarta.ws.rs.core.Application application) {
        this.application = application;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
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

    public SSLClientAuthentication getSslClientAuthentication() {
        return sslClientAuthentication;
    }

    public void setSslClientAuthentication(SSLClientAuthentication sslClientAuthentication) {
        this.sslClientAuthentication = sslClientAuthentication;
    }

    public static class Builder {

        private Protocol protocol;
        private String rootPath;
        private String tls;
        private String host;
        private Integer port;
        private Boolean logo;
        private jakarta.ws.rs.core.Application application;
        private SSLClientAuthentication sslClientAuthentication;
    @NotNull
    private Boolean runInternalTest = Boolean.FALSE;
        public Builder sslClientAuthentication(SSLClientAuthentication sslClientAuthentication) {
            this.sslClientAuthentication = sslClientAuthentication;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder logo(Boolean logo) {
            this.logo = logo;
            return this;
        }
        public Builder runInternalTest(Boolean runInternalTest) {
            this.runInternalTest = runInternalTest;
            return this;
        }

        public Builder rootPath(String rootPath) {
            this.rootPath = rootPath;
            return this;
        }

        public Builder tls(String tls) {
            this.tls = tls;
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
                System.out.println("....... running startNew......");
                if (rootPath == null) {
                    System.out.println("please enter rootPath");
                    return new JettraServer();
                }
                var protocolString = protocol == Protocol.HTTP ? "HTTP" : "HTTPS";

                protocolString = protocolString.toUpperCase();
                tls = tls == null ? "" : tls;

                if (protocolString.equals("HTTP") && (!tls.equals(""))) {
                    System.out.println("tls is only used with HTTPS. ");
                    return new JettraServer();
                }

                long start = System.currentTimeMillis();

                System.out.println("\n___________________________________________________________________________");
                System.out.println(
                        "                         JettraServer starting....");
                final SeBootstrap.Configuration requestedConfiguration;

                if (tls.equals("")) {
                    requestedConfiguration = SeBootstrap.Configuration.builder().protocol(protocolString).host(host)
                            .port(port).rootPath(rootPath).sslClientAuthentication(sslClientAuthentication).build();
                } else {
                    SSLContext tlsContext = SSLContext.getInstance(tls);
                    requestedConfiguration = SeBootstrap.Configuration.builder().protocol(protocolString).host(host)
                            .port(port).rootPath(rootPath).sslClientAuthentication(sslClientAuthentication).sslContext(tlsContext).build();

                }

                SeBootstrap.start(application, requestedConfiguration).thenAccept(instance -> {
                    try {
                        instance.stopOnShutdown(stopResult
                                -> System.out.printf("Stop result: %s [Native stop result: %s].%n", stopResult,
                                        stopResult.unwrap(Object.class)));

                        long finish = System.currentTimeMillis();
                        long timeElapsed = finish - start;
                        System.out.println("\n");
                        if (logo) {
                            JettraLogo.blurVision();
                        }

                        System.out.println("\tServer started in: " + timeElapsed + "ms\n");
                        Boolean test = true;
                        UriBuilder uriBuilder = instance.configuration().baseUriBuilder();
                        var template = uriBuilder.toTemplate();
                        System.out.println("[URI]: "+template);
                      if (runInternalTest) {

                            var httpClient = HttpClient.newBuilder().build();
                            var httpRequest = HttpRequest.newBuilder()
                                    .uri(uriBuilder
                                            .path("jettrahello").build())
                                    .header("Content-Type", "application/json")
                                    .GET().build();

                            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                            var body = response.body();
                            System.out.printf("[Instance: %s]  \n[Native handle: %s].\n[Test endpoint: %s]", instance, instance.unwrap(Object.class), uriBuilder);

                            if (body == null || body.equals("")) {
                                JettraMessage.failedTest();
                            } else {
                                System.out.println("\n[Result]: " + body);
                            }
                             finish = System.currentTimeMillis();
                        timeElapsed = finish - start;
                        System.out.println("\n\n");

                        System.out.println("\tServer started in WITH TEST: " + timeElapsed + "ms");
                      }

                       

                        System.out.println("..............................");
                        System.out.println("\tSend SIGKILL to shutdown.");
                        System.out.println("..............................");
                    } catch (IOException ex) {
                        Logger.getLogger(JettraServer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(JettraServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });

                Thread.currentThread().join();

            } catch (Exception e) {
                System.out.println("startNew() " + e.getLocalizedMessage());
            }

            return new JettraServer(rootPath, tls, host, port, logo, sslClientAuthentication, application, runInternalTest);
        }

    }

    public static Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    private static void log(final String fmt, final Object... args) {

        System.out.printf(fmt, args);
        System.out.println();

    }

}
