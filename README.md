##Spring Boot简介
　　Spring作为一个轻量级的容器,在JaveEE开发中得到了广泛的应用，但是Spring的配置繁琐且臃肿，在和各种第三方框架整合时代码量都非常大，而且大部分代码都是重复的。为了改善这种状态，使开发者能够快速上手Spring
，Spring Boot应运而生。  

　　Spring Boot带来了全新的自动化配置解决方案，使用Spring Boot可以快速创建基于Spring生产级的独立应用程序。Spring Boot
中对一些常用的第三方库提供了默认的自动化配置方案，使得开发者只需要很少的Spring配置就能运行一个完整的Java EE应用。Spring Boot项目可以采用传统的方案打成war包，然后部署到Tomcat中运行。也可以直接打成可执行jar包，这样通过java -jar
命令就可以启动一个Spring Boot项目。  

　　总体来说，Spring Boot主要有如下优势：  
　　• 提供一个快速的Spring项目搭建渠道。  
　　• 开箱即用，很少的Spring配直就能运行一个Java EE项目。  
　　• 提供了生产级的服务监控方案。  
　　• 内嵌服务器，可以快速部署。  
　　• 提供了一系列非功能性的通用配置。  
　　• 纯Java配直，没有代码生成， 也不需要XML配置。  

---

##创建Maven工程

###1、使用命令创建Maven工程
　　创建命令：
　　`mvn archetype:generate -DgroupId=con.java.apps -DartifactId=XXX -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false`  
　　命令解释：  
　　• -DgroupId 组织Id （项目包名）。  
　　• -DartifactId ArtifactId （项目名称或者模块名称）。  
　　• -DarchetypeArtifactId 项目骨架。  
　　• -DinteractiveMode 是否使用交互模式。  

###2、在Eclipse中创建Maven工程
　　1）File-->New-->Maven Project  
　　2）选中 Use default Workspace location复选框  
　　3）选择项目骨架，保持默认即可  
　　4）输入项目信息  
　　　　Group Id  
　　　　Artifact Id  
　　　　Version  
　　　　Package  

###3、使用IntelliJ IDEA创建Maven工程
　　1）创建项目时选择Maven  
　　2）不必选择骨架  
　　3）输入项目信息  
　　　　GroupId  
　　　　ArtifactId  
　　　　Version  
　　4）选择项目位置  

---



　　
##创建Spring Boot工程

###1、在线创建  
　　网址：[Spring Initializr](https://start.spring.io) 

###2、使用IntelliJ IDEA创建  
　　New Project-->Spring Initializr  

###3、使用STS创建  
　　New-->Spring Starter Project  

---
##运行Spring Boot工程

###1、使用mvn命令运行
　　运行命令：  
　　`mvn spring也oot : run`  

###2、直接执行main方法运行

###3、打包运行
　　打包命令：  
　　`mvn package`  
　　执行命令：  
　　`java -jar springboot-application.jar`  

---

##常用starter简介
**spring-boot-starter-parent**  
**spring-boot-starter**  
**spring-boot-starter-web**  
