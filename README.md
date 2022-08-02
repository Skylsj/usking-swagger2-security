# usking-swagger2-security

#### 介绍
在springboot开发REST接口的项目中，使用swagger定义接口。
此插件用来给访问swagger-ui.html接口页面加登录验证，增加接口的安全性。
无代码侵入，加入、卸载方便。按说明配置即可。

<font color=#FF0000 >__注意：__</font>此插件只是swagger-ui.html页面访问的验证，对接口并不做验证，所以接口仍然可以访问，并不需要验证。

如果不想本地编译，只需要把dist目录下的usking-swagger2-security-1.0.0.jar下载下来即可。

#### 软件架构
usking-tools-log使用的技术：

1. 使用springboot-starter的原理实现了按条件加载。
2. 利用Filter拦截技术，对请求进行验证。

#### 安装教程

1. 引入usking-swagger2-security-1.0.0.jar
```xml
<dependency>
   <groupId>top.hisoft.swagger.security</groupId>
   <artifactId>usking-swagger2-security</artifactId>
   <version>1.0.0</version>
</dependency>
```
2. 配置参数
* application.properties 格式配置
```properties
# 启用usking-swagger2-security程序，默认值false
swagger2.basic.enable=true
# 用户，默认值sky
swagger2.basic.username=sky
# 密码，默认值000000
swagger2.basic.password=000000
```

* application.yml 格式配置
```yaml
swagger2:
   basic:
    enable: true
    username: sky
    password: 000000
```
<font color=#FF0000 >__注意：__ 如果用默认用户、密码只需要配置开启验证即可（swagger2.basic.enable=true）。</font>

#### 版本说明
* 1.0.0 发布初版