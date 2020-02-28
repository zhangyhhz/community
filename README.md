## Springboot学习

### 资料

参考论坛：https://elasticsearch.cn

学习视频：https://www.bilibili.com/video/av65117012?p=67

### 相关链接
Springboot Guides：https://spring.io/guides

BootStrap：https://v3.bootcss.com/

Github OAuth：https://developer.github.com/apps/building-oauth-apps/

OkHttp：https://square.github.io/okhttp/

Thymeleaf：https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html

JQuery：https://api.jquery.com/

### 工具
Flyway：https://flywaydb.org/documentation/maven/

Lombok：https://projectlombok.org/setup/maven

Markdown插件：https://pandao.github.io/editor.md/

### bash
mvn flyway:migrate

mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

### 部署
#### 依赖
- Git
- JDK
- Maven
- MySQL
#### 步骤
- yum update
- yum install git
- mkdir App
- cd App
- git clone 地址
- yum install maven
- mvn compile package (编译打包)
- cp src/main/resources/application.properties src/main/resources/application-production.properties 
- vim src/main/resources/application-production.properties 复制配置文件进行编辑(不能将配置放到代码库中，且为了服务端配置不冲突，因此需要复制一个新的配置文件)
- mvn package 
- java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar (运行命令)
