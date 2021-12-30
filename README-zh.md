spring-boot-polaris
========================================
[English](./README.md) | 简体中文 

---

spring-boot-polaris是北极星网格对接 SpringBoot 的适配组件，基于 SpringBoot 框架的开发者可以使用这些组件快速接入北极星网格进行分布式应用的开发。

## 如何构建

spring-boot-polaris使用Maven进行构建，最低支持JDK 1.8。将本项目clone到本地后，执行以下命令进行构建：
```
mvn clean install
```

## 如何使用

### 如何引入依赖

在 dependencyManagement 中添加如下配置：
```xml
<dependencyManagement>        
    <dependencies>
        <dependency>
            <groupId>com.tencent.polaris</groupId>
            <artifactId>spring-boot-polaris-dependencies</artifactId>
            <version>1.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
然后在 dependencies 中添加自己所需使用的依赖即可使用。

## 功能样例

为了演示功能如何使用，spring-boot-polaris 项目包含了一个子模块spring-boot-polaris-examples。此模块中提供了演示用的 example ，您可以阅读对应的 example 工程下的 README-zh 文档，根据里面的步骤来体验。

[快速开始样例](https://github.com/polarismesh/spring-boot-polaris/tree/main/spring-boot-polaris-examples/quickstart-example/README-zh.md)