# MusicShareServerJava
MusicShareSwift项目的Server端代码

# Code by Java

# 基本说明
Prerequisite 先决条件
---
确保计算机中安装了下列组件：

* Java Runtime Environment 1.8 （运行时）
* Apache Tomcat 8.0+ （8.0.14 为最佳兼容版本）
* Apache Maven 3+ （作为包管理工具）
* Eclipse （调试工具）

Debugging / Running 调试与运行
---
* 如果需要调试，将项目导入到 Eclipse 中（建议安装 `m2e` 插件）。
* 按下列顺序对各个项目执行`mvn install`：
  * infrastructure
  * musician-core
  * musician-repository
  * musician-service
  * musician-app-server
* 将 musician-app-server 项目中 `eric.clapton.musician.test` 包下的 `StartTomcat8.java`作为普通 Java 应用程序运行。

Unit Test 单元测试
---
已为单元测试提供了支持。
可以直接用 JUnit 执行 musician-app-server 项目中 `eric.clapton.musician.test` 包（含子包）下以 `Test` 结尾的测试用例。