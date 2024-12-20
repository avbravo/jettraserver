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



```shell

mvn clean package -Pexec

java -jar target/jettraserver.jar



```