# Jettra Server

# Example text {style=text-align:center}

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



---
# Maven Properties

```
Maven Properties Guide
Creado por Karl Heinz Marbaise, modificado por última vez por Herve Boutemy el sep 05, 2015
Full detailed explanations can be found in Maven Model Builder Interpolation reference documentation.

This page extracts a few classical values:
${project.basedir} 
This references to the root folder of the module/project (the location where the current pom.xml file is located)
POM properties referencing useful build locations, with default values defined in the Super POM:

${project.build.directory}
This represents by default the target folder.
${project.build.outputDirectory}
This represents by default the target/classes folder.
${project.build.testOutputDirectory}
This represents by default the target/test-classes folder.
${project.build.sourceDirectory}
This represents by default the src/main/java folder.
${project.build.testSourceDirectory}
This represents by default the src/test/java folder.
You can use further properties like the following:

${project.build.finalName}
This is by default defined as ${project.artifactId}-${project.version}.
${project.version}
This can be used at locations where you have to write a literal version otherwise, in particular if you are in a multi-modules build for inter modules dependencies.
User Settings

The settings.xml elements could be referenced by using things like this (see also at the Super POM):

${settings.localRepository}
which references the location of the local repository. This is by default ${home}/.m2/repository.
`


``