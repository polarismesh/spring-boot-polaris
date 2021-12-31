# QuickStart Example

## Example Instruction

This example illustrates how to use spring-boot-polaris for consumer or provider applications to connect to polaris, and complete the service invocation.

## Example

### Connect to polaris

1. Add dependency spring-boot-polaris-discovery-starter in the pom.xml file in your project.
```
<dependency>
    <groupId>com.tencent.polaris</groupId>
    <artifactId>spring-boot-polaris-discovery-starter</artifactId>
</dependency>
```

2. Add polaris server address configurations to file /src/main/resources/application.properties.
```
polaris.address=grpc://127.0.0.1:8091
```

### Start Example

1. Start in IDE:

- as provider: Find main class `EchoServerApplication` in project `quickstart-provider`, then execute the main method.
- as consumer: Find main class `EchoClientApplication` in project `quickstart-consumer`, then execute the main method.

2. Build a jar:

- as provider: Execute command `mvn clean package` in project `quickstart-provider` to build a jar, then execute the jar with `java -jar ${jar-file}`
- as consumer: Execute command `mvn clean package` in project `quickstart-consumer` to build a jar, then execute the jar with `java -jar ${jar-file}`

### Verify

#### Check polaris console

Login into polaris console, and check the instances in Service `EchoServerBoot` and `EchoClientBoot`.

#### Invoke by http call

Invoke http call，replace `${app.port}` to the consumer port (11011 by default).
```shell
curl -L -X GET 'http://localhost:${app.port}/echo?value=hello_world''
```

expect：`echo: hello_world`