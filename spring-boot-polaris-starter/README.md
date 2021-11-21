# sr-product-account-sdk 发布 [V1.x.x]

本组件封装日cps账号获取方法.

## 功能特性

### 一、支持根据kaId获取账号信息
```java
    /**
     * 根据KaId获取账号信息.
     */
    @RequestLine("POST /account/from/ka")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    R getFromKa(GetAccountFromKaReq req);
```
### 二、根据warehouseId获取账号信息
```java
    /**
     * 根据warehouseId获取账号信息.
     */
    @RequestLine("POST /account/from/warehouse")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    R getFromWarehouse(GetAccountFromWarehouseReq req);
```

### 三、根据source获取账号信息
```java
    /**
     * 根据source获取warehouse.
     */
    @RequestLine("POST /account/from/source")
    R getWarehouseFromSource(GetAccountFromSourceReq req);
```



## 步骤一：增加Maven仓库
```xml
    <repositories>
	    <repository>
            <id>pcm_common_public</id>
            <name>internal repository for releases</name>
            <url>https://mirrors.tencent.com/repository/maven/pcm-common</url>
        </repository>
        <repository>
            <id>pcm_common_snapshot</id>
            <name>internal repository for snapshots</name>
            <url>https://mirrors.tencent.com/repository/maven/pcm-common-snapshot</url>
        </repository>
    </repositories>

```

## 步骤二：增加Starter
```xml
        <dependency>
            <groupId>com.tencent.sr.product</groupId>
            <artifactId>sr-product-account-sdk</artifactId>
            <version>1.x.x</version>
        </dependency>
```

## 步骤三：根据环境增加application.properties配置

* 测试环境
```properties
# CLB地址（必填）
spring.cps.account.client.url=http://11.135.221.252:8080/cps
# CL5地址（必填）
spring.cps.account.client.namespace=Test
spring.cps.account.client.service=sr-product-account-server
# 分配的秘钥（必填，人工分配）
spring.cps.account.client.secret-id=sr-product-account-sdk-test
# 版本号(可选，特殊情况下才需要配置)
spring.cps.account.client.version=1.0.0
# 缓存过期时间，默认5分钟(可选)
spring.cps.account.client.cache.expire=5
# 缓存默认容量(可选)
spring.cps.account.client.cache.initial-capacity=100
# 缓存最大容量(可选)
spring.cps.account.client.cache.maximum-size=1000

```

* 正式环境
```properties
# CLB地址（必填）
spring.cps.account.client.url=http://11.135.221.199:8080/cps
# CL5地址（必填）
spring.cps.account.client.namespace=Production
spring.cps.account.client.service=sr-product-account-server
# 分配的秘钥（必填，人工分配）
spring.cps.account.client.secret-id=人工分配
# 版本号(可选，特殊情况下才需要配置)
spring.cps.account.client.version=1.0.0
# 缓存过期时间，默认5分钟(可选)
spring.cps.account.client.cache.expire=5
# 缓存默认容量(可选)
spring.cps.account.client.cache.initial-capacity=100
# 缓存最大容量(可选)
spring.cps.account.client.cache.maximum-size=1000

```


## 步骤四：使用例子
```java

class ProductAccountServiceTest {

    @Resource
    private ProductAccountService productAccountService;

    @RepeatedTest(10)
    void getFromKa() {

        /*
         * 首次没有数据.
         */
        GetAccountFromKaReq req = new GetAccountFromKaReq();
        req.setKaId(9999L);

        R ret = productAccountService.getFromKa(req);
        log.info("---------------------------");
        log.info("{}", ret);
        assertThat(ret.ok()).isFalse();
    }

}
```
