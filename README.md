spring-boot-polaris
========================================
English | [简体中文](./README-zh.md)

---

spring-boot-polaris is the polaris adapter for SpringBoot，application based on SpringBoot can use spring-boot-polaris access polaris quickly.

## How to build

spring-boot-polaris uses Maven for most build-related activities, and JDK 1.8 or later versions are supported.
You should be able to get off the ground quite quickly by cloning the project you are interested in and typing:
 ```
 mvn clean install
 ```
 
 ## How to Use
 
 ### Add maven dependency
 
 These artifacts are available from Maven Central via BOM:
 ```xml
<dependencyManagement>        
    <dependencies>
        <dependency>
            <groupId>cn.polarismesh</groupId>
            <artifactId>spring-boot-polaris-dependencies</artifactId>
            <version>1.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
 ```
 add the module in dependencies.
 
 ## Examples
 
 A spring-boot-polaris-examples module is included in our project for you to get started with spring-boot-polaris quickly. It contains an example, and you can refer to the readme file in the example project for a quick walkthrough.
 
 [QuickStart Example](https://github.com/polarismesh/spring-boot-polaris/tree/main/spring-boot-polaris-examples/quickstart-example)
 
