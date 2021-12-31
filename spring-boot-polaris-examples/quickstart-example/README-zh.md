# 快速开始样例

## 样例说明

本样例演示如何使用 spring-boot-polaris 完成被调端以及主调端应用接入polaris，并完成服务调用流程。

## 样例

### 如何接入

1. 首先，修改 pom.xml 文件，引入 spring-boot-polaris-discovery-starter。
```
<dependency>
    <groupId>com.tencent.polaris</groupId>
    <artifactId>spring-boot-polaris-discovery-starter</artifactId>
</dependency>
```

2. 在应用的 /src/main/resources/application.properties 配置文件中配置 Polaris Server 地址。
```
polaris.address=grpc://127.0.0.1:8091
```

### 执行样例

1. IDE直接启动：

- 启动被调方：找到 `quickstart-provider` 项目的主类 `EchoServerApplication`，执行 main 方法启动。
- 启动主调方：找到 `quickstart-consumer` 项目的主类 `EchoClientApplication`，执行 main 方法启动。

2. 打包编译后启动：

- 启动被调方：找到 `quickstart-provider` 项目下，执行 `mvn clean package` 将工程编译打包，然后执行 `java -jar quickstart-provider-${version}.jar`
- 启动主调方：找到 `quickstart-consumer` 项目下，执行 `mvn clean package` 将工程编译打包，然后执行 `java -jar quickstart-consumer-${version}.jar`

### 验证

#### 控制台验证

登录polaris控制台，可以看到EchoServerBoot以及EchoClientBoot服务下存在对应的实例。

#### HTTP调用

执行http调用，其中`${app.port}`替换为consumer的监听端口（默认为11011）。
```shell
curl -L -X GET 'http://localhost:${app.port}/echo?value=hello_world''
```

预期返回值：`echo: hello_world`