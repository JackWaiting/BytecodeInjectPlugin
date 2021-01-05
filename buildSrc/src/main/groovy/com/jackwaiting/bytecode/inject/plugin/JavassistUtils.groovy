package com.jackwaiting.bytecode.inject.plugin


import javassist.CtBehavior
import javassist.CtClass
import javassist.CtConstructor
import javassist.CtMethod
import javassist.bytecode.AccessFlag
import javassist.bytecode.CodeAttribute
import javassist.bytecode.LineNumberAttribute

class JavassistUtils {

    public static final int EMPTY_METHOD_LENGTH = 2
    public static final int EMPTY_CONSTRUCTOR_LENGTH = 4

    static int getBehaviourLength(CtBehavior behavior) {
        CodeAttribute ca = behavior.getMethodInfo().getCodeAttribute()
        if (ca == null) return -1
        LineNumberAttribute info = (LineNumberAttribute) ca.getAttribute(LineNumberAttribute.tag)
        if (info == null) return -1
        return info.tableLength()
    }

    static int getConstructorLength(CtConstructor constructor) {
        return getBehaviourLength(constructor)
    }

    static int getMethodLength(CtMethod method) {
        return getBehaviourLength(method)
    }

    static boolean isEmptyMethod(CtMethod method) {
        return getMethodLength(method) < EMPTY_METHOD_LENGTH
    }

    static boolean isEmptyConstructor(CtConstructor constructor) {
        return getConstructorLength(constructor) < EMPTY_CONSTRUCTOR_LENGTH
    }

    static boolean acceptCtClass(CtClass ctClass,BytecodeInjectExtension ext) {
        if (ctClass.isInterface()) {
            return false
        }
        // 不处理Android生成的类
        if (TransformUtils.isAndroidGeneratedClasses(ctClass)) {
            return false
        }
        // 不处理Plugin自身所在的包。否则会导致App无法运行。
        if (ctClass.name.startsWith(Constants.PLUGIN_PACKAGE)) {
            return false
        }
        if (ext.processClassesRegex != null) {
            return ctClass.name.matches(ext.processClassesRegex);
        }
        // 默认：处理所有类
        return true
    }

    static boolean acceptCtBehavior(CtBehavior it, BytecodeInjectExtension ext) {
        if (it.getMethodInfo().isStaticInitializer()) {
            return false
        }
        if (it.isEmpty()) {
            return false
        }
        // 跳过synthetic方法，例如AsyncTask会生成同名synthetic方法
        if ((it.getModifiers() & AccessFlag.SYNTHETIC) != 0) {
            return false
        }
        if ((it.getModifiers() & AccessFlag.ABSTRACT) != 0) {
            return false
        }
        if ((it.getModifiers() & AccessFlag.NATIVE) != 0) {
            return false
        }
        if ((it.getModifiers() & AccessFlag.INTERFACE) != 0) {
            return false
        }
        if (ext.skipConstructor && it.methodInfo.isConstructor()) { // 跳过构造函数
            return false
        }
        if (it instanceof CtConstructor && isEmptyConstructor((CtConstructor) it)) {
            // 跳过空构造函数
            return false
        }
        if (ext.skipStaticMethod && (it.getModifiers() & AccessFlag.STATIC) != 0) { // 跳过静态方法
            return false
        }
       /*if (ext.skipSimpleMethod && it instanceof CtMethod && isSimpleMethod((CtMethod) it,ext)) {
            // 跳过简单方法
            return false
        }*/
        return true
    }

    private boolean isSimpleMethod(CtMethod method,BytecodeInjectExtension ext) {
        return getMethodLength(method) < Math.max(ext.simpleMethodLength, EMPTY_METHOD_LENGTH)
    }
}
