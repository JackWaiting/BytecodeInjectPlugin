package com.jackwaiting.bytecode.inject.plugin

import javassist.CtClass


class BytecodeInjectExtension {

    /**
     * 是否启用
     */
    Boolean enable = false

    /**
     * 要处理的类，正则表达式
     */
    String processClassesRegex = null
    /**
     * 插入方法变量，详见Constants中的定义
     */
    String codeLocalVariable = Constants.DEFAULT_LOCAL_VARIABLE

    String codeLocalVariableType = ByteCode.INT

    /**
     * 插入方法开始处的代码。支持变量，详见Constants中的定义
     */
    String codeBeforeMethod = Constants.DEFAULT_METHOD_START
    /**
     * 插入方法结束处的代码。支持变量，详见Constants中的定义
     */
    String codeAfterMethod = Constants.DEFAULT_METHOD_END


    // log输出
    Boolean enableClassLog = true
    Boolean enableMethodLog = true
    Boolean enableStackLog = true
    // 跳过方法
    Boolean skipConstructor = false
    Boolean skipStaticMethod = false

    Boolean skipSimpleMethod = true
    Integer simpleMethodLength = 1

}
