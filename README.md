# 最新版本
v1.0.4
Copyright (C) 2019-2021 by JackWaiting, All rights reserved.

## 介绍

一个使用 Gradle Plugin + Javassist 实现代码自动注入的库

## 支持功能
- 1、支持代码自定义插入方法体
- 2、支持插入自定义变量名 (支持 byte,short,int long,boolean,char,float,double)
- 3、适配 Java 与 Kotlin 代码
- 4、支持 Module 与 Lib 同时代码插入
- 5、支持简单变量标签，包括（class-name，simple-class-name，method-name）

## Extension 介绍

- enable 是否开启 BytecodeInjectPlugin 注入开关
- processClassesRegex 要处理的类，正则表达式
- codeLocalVariable 需要插入的方法变量
- codeLocalVariableType 需要插入的方法变量类型
- codeBeforeMethod 插入方法前的具体执行代码
- codeAfterMethod 插入方法后的具体执行代码
- enableClassLog & enableMethodLog 是否输出日志开关

## 使用方法介绍

往所有方法中插入执行时间统计代码，优化耗时方法。

### 1、配置 gralde 

    buildscript {

        repositories {
            maven { url "https://dl.bintray.com/jackwaiting/maven/" }
        }
        dependencies {
            classpath 'com.jackwaiting.bytecode.inject.plugin:bytecode-inject-release:1.0.4'
        }
    }

### 2、module gradle 使用插件

    apply plugin: 'bytecode-inject-android'

    jack_extensions {
      enable true  //是否插入代码开关

      // 指定插入到方法开头的变量与变量类型
      codeLocalVariable "startTime"  //定义变量
      codeLocalVariableType "long" //定义变量类型

      // 指定插入到方法开头和结束处的代码,支持简单变量，包括<class-name>，<simple-class-name>，<method-name>
      codeBeforeMethod "startTime = System.currentTimeMillis();"
      codeAfterMethod "System.out.println(\"（bytecode-inject-android）<class-name>\$<method-name> 执行的时间： \" " +
              "+(System.currentTimeMillis() - startTime) +\"ms\");"
    }
    
### 3、 执行日志效果

- 2021-01-05 12:23:16.604 19195-19195/com.jackwaiting.gradleplugin I/System.out: （bytecode-inject-android）com.jackwaiting.gradleplugin.MainActivity$onResume 执行的时间： 11ms
