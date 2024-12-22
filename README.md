# Jettra Server


1. Importe la libreria a su proyecto Java SE
2. Crea el archivo src/main/resources/META-INF/beans.xml
3.Crea el archivo src/main/resources/META-INF/microprofile-config.properties

En el metodo main

```java

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        JettraServer persona = new JettraServer.Builder()
                .protocol("HTTP")
                .host("localhost")
                .port(8080)
                .application(
                        new JakartaRestConfiguration() {
                    @Override
                    public Set<Class<?>> getClasses() {
                        Set<Class<?>> classes = new HashSet<>();
                        classes.add(EmployeeController.class);
                        classes.add(HelloController.class);
                        return classes;
                    }
                }
                )
                .start();

    }
}


```

---

## Con HTTPS/TLSv1.2

```java
  JettraServer persona = new JettraServer.Builder()
                .protocol("HTTPS")
                .host("localhost")
                .port(8080)
                .tls("TLSv1.2")
                .application(
                        new JakartaRestConfiguration() {
                    @Override
                    public Set<Class<?>> getClasses() {
                        Set<Class<?>> classes = new HashSet<>();
                        classes.add(EmployeeController.class);
                        classes.add(HelloController.class);
                        return classes;
                    }
                }
                )
                .start();

```



```shell

mvn clean package -Pexec  -Dmaven.test.skip=true  

java -jar target/jettraserver.jar



```
## Docker


 <plugins>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.4.5</version>
                <configuration>
                    <imageName>springdocker</imageName>
                    <baseImage>java</baseImage>
                    <entryPoint>["java", "-jar", "/${project.build.finalName}.jar"]</entryPoint>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
          
        </plugins>


$ mvn clean package docker:build
$ docker images
$ docker run -p 8080:8080 -t <image name>