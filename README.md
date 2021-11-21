
# spring-boot-polaris 发布 [V1.0.0]

SpringBoot 更加优雅地整合北极星SDK.

## 一、开发环境

-   jdk 1.8
-   maven 3.5.3
-   lombok 1.16.20
-   springboot 2.4.3
-   polaris 3.0.8
-   openFeign 11.1

## 二、快速接入

spring-boot-polaris-starter 是基于feign专门为SpringBoot使用者提供快速接入方式.

### 1、添加依赖
```xml
       <dependency>
            <groupId>com.tencent.nameservice</groupId>
            <artifactId>spring-boot-polaris-starter</artifactId>
            <version>${project.version}</version>
        </dependency>
```

### 2、服务注册

（1） 在启动程序入口类添加注解`@EnablePolarisProvider`。
```java
@EnablePolarisProvider
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

}
```
（2）在应用配置文件application.properties配置北极星注册的服务信息
```properties
## 在北极星控制台配置的环境
spring.polaris.provider.namespace=Development
## 在北极星控制台创建的服务名称
spring.polaris.provider.service=polaris-book-service
## 在北极星控制台创建的服务的token
spring.polaris.provider.token=732454617cb94125930d1352237150a6
```

### 3、服务发现

1、在启动程序入口类添加注解`@PolarisConsumerScan`，并配置扫描远程服务路径.
```java
@PolarisConsumerScan("com.tencent.nameservice.sdk.consumer.remote")
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
```

2、在上文配置的扫描package路径中，添加远程服务接口，如BookClient.java，并添加注解`@PolarisConsumer`表明该类为远程服务类，其注解属性`id`为该类唯一标志，
可以使用这个标志在应用配置文件application.properties中配置远程服务信息.
```java
@PolarisConsumer(id = "book")
public interface BookClient {

    @RequestLine("GET /book/{id}")
    R<Book> getBook(@Param("id") String id);

}
```

3、为远程服务类BookClient配置远程服务信息.
```properties
## 以下为为远程服务类id=book配置远程服务信息

## 服务提供者在北极星控制台配置的环境
spring.polaris.consumer.book.namespace=Development
## 服务提供者在北极星控制台配置的服务名称
spring.polaris.consumer.book.service=polaris-book-service
```

4、远程调用例子
```java
@Component
public class DemoService {

    @Resource
    private BookClient bookClient;

    @Override
    public R<Book> getBook()  {

        return bookClient.getBook("bookId");
    }
}

```


## 三、版本变更记录

* 20211121 v1.0.0 发布

