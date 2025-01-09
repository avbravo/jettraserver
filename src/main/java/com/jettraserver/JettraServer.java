/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jettraserver;

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
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.net.ssl.SSLContext;

/**
 *
 * @author avbravo Se utiliza para gestionar los datos leidos del repositorio
 */
public class JettraServer {

    private String protocol;

    @NotNull(message = "EL rootPath no debe estar vacio")
    private String rootPath;
    private String tls;
    private String host;
    private Integer port;
    private Boolean logo;

    private jakarta.ws.rs.core.Application application;

    public JettraServer() {
    }

    public JettraServer(String protocol, String rootPath, String tls, String host, Integer port, Boolean logo, Application application) {
        this.protocol = protocol;
        this.rootPath = rootPath;
        this.tls = tls;
        this.host = host;
        this.port = port;
        this.logo = logo;
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
        private String rootPath;
        private String tls;
        private String host;
        private Integer port;
        private Boolean logo;
        private jakarta.ws.rs.core.Application application;

        public Builder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder logo(Boolean logo) {
            this.logo = logo;
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
                System.out.println("....... running......");
                if (rootPath == null) {
                    System.out.println("please enter rootPath");
                    return new JettraServer();
                }
                protocol = protocol.toUpperCase();
                if (tls == null) {
                    tls = "";
                }
                if (protocol.equals("HTTP") && (!tls.equals(""))) {
                    System.out.println("tls is only used with HTTPS. ");
                    return new JettraServer();
                }

                long start = System.currentTimeMillis();

                System.out.println("");

                System.out.println(
                        "___________________________________________________________________________");
                System.out.println(
                        "                         JettraServer starting....");

                SeBootstrap.Configuration.Builder configBuilder = SeBootstrap.Configuration.builder();
                System.out.println("paso 1");
                if (protocol.equals("HTTP")) {
                    configBuilder.property(SeBootstrap.Configuration.PROTOCOL, protocol);
                } else {
                    if (tls == "" || tls == null || tls.isEmpty()) {
                        configBuilder.property(SeBootstrap.Configuration.PROTOCOL, protocol).sslClientAuthentication(SSLClientAuthentication.MANDATORY);
                    } else {
                        SSLContext tlsContext = SSLContext.getInstance(tls);
                        configBuilder.property(SeBootstrap.Configuration.PROTOCOL, protocol).sslClientAuthentication(SSLClientAuthentication.MANDATORY).sslContext(tlsContext);
                    }
                }
                System.out.println("paso 2");
                if (!rootPath.equals("")) {
                    configBuilder.property(SeBootstrap.Configuration.ROOT_PATH, rootPath);
                }
                configBuilder.property(SeBootstrap.Configuration.HOST, host);
                configBuilder.property(SeBootstrap.Configuration.PORT, port);

                System.out.println("paso 3");
                System.out.println("..........................................................");
                System.out.println("application " + application.toString());
                System.out.println("host " + host);
                System.out.println("port " + port);
                System.out.println("rootPath " + rootPath);
                System.out.println("..........................................................");
                SeBootstrap.Instance instance = SeBootstrap.start(application, configBuilder.build()).toCompletableFuture().get();
                System.out.println("paso 3.1");
                instance.stopOnShutdown(stopResult
                        -> System.out.printf("Stop result: %s [Native stop result: %s].%n", stopResult,
                                stopResult.unwrap(Object.class)));

                System.out.println("paso 4");
                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;
                if (logo) {
                    JettraLogo.blurVision();
                }

                System.out.println("paso 5");
                System.out.println("Server started in: " + timeElapsed + "ms");
                System.out.println("\n\n");
                UriBuilder uriBuilder = instance.configuration().baseUriBuilder();
                var httpClient = HttpClient.newBuilder().build();
                var httpRequest = HttpRequest.newBuilder()
                        .uri(uriBuilder
                                .path("jettrahello").build())
                        .header("Content-Type", "application/json")
                        .GET().build();
                System.out.println("paso 6");
                HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                var body = response.body();
                System.out.println("paso 7");
                System.out.printf("\tInstance: %s  \n\t[Native handle: %s].%n \n\tTest connection to: %s", instance, instance.unwrap(Object.class), uriBuilder);
                //System.out.printf("\tInstance: %s \n\trunning at: %s \n\t[Native handle: %s].%n", instance, uriBuilder,instance.unwrap(Object.class));

                if (body == null || body.equals("")) {
                    JettraMessage.failedTest();
                } else {
                    System.out.println("\n\tResult: " + body);
                }
                System.out.println("paso 8");
                System.out.println("");
            } catch (Exception e) {
                System.out.println("start() " + e.getLocalizedMessage());
            }

            return new JettraServer(protocol, rootPath, tls, host, port, logo, application);
        }
        public JettraServer jaxRun() {
            try {
                System.out.println("____________________________________________________________________");
                System.out.println("......... llego a JaxRun");
                
                System.out.println("____________________________________________________________________");
                if (rootPath == null) {
                    System.out.println("please enter rootPath");
                    return new JettraServer();
                }
                protocol = protocol.toUpperCase();
                if (tls == null) {
                    tls = "";
                }
                if (protocol.equals("HTTP") && (!tls.equals(""))) {
                    System.out.println("tls is only used with HTTPS. ");
                    return new JettraServer();
                }

                long start = System.currentTimeMillis();

                System.out.println("");

                System.out.println(
                        "___________________________________________________________________________");
                System.out.println(
                        "                         JettraServer starting....");
                
                
                
             //    System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
        SeBootstrap.Configuration configuration = SeBootstrap.Configuration.builder()
                .host(host)
                .port(port)
                .protocol(protocol.toLowerCase())
.rootPath(rootPath)
                .build();

        SeBootstrap.start(application,configuration)
                .thenAccept(instance -> {
                    instance.stopOnShutdown(stopResult -> System.out.printf("Stopped container (%s)", stopResult.unwrap(Object.class)));
                    log("REST Server running at %s",
                            instance.configuration().baseUri());
                    log("Example: %s",
                            instance.configuration().baseUriBuilder().path("rest/frank").build());
                    log("Send SIGKILL to shutdown container");
                });
        Thread.currentThread().join();
                
                
                
                
                
                
                
                
                
                
                

            } catch (Exception e) {
                System.out.println("start() " + e.getLocalizedMessage());
            }

            return new JettraServer(protocol, rootPath, tls, host, port, logo, application);
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
