package com.jackwaiting.bytecode.inject.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.google.common.collect.ImmutableSet
import javassist.*
import org.gradle.api.Project

import java.util.concurrent.ForkJoinPool

class BytecodeInjectTransform extends Transform {


    Project target
    BytecodeInjectExtension ext = null
    ArrayList<CtClass> classes = new ArrayList<>()

    BytecodeInjectTransform(Project target) {
        this.target = target
    }

    @Override
    String getName() {
        return Constants.BYTECODE_INJECT_PLUGIN_NAME
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return ImmutableSet.of(QualifiedContent.Scope.PROJECT,QualifiedContent.Scope.SUB_PROJECTS)
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental)
            throws IOException, TransformException, InterruptedException {

        println("start transform")
        if (ext == null) {
            ext = target.extensions.getByName(Constants.EXTENSIONS_NAME)
            println("ext = ${ext.processClassesRegex},  ${ext.codeBeforeMethod} , ${ext.codeAfterMethod}")
        }

        if (ext == null || !ext.enable ||
                (isEmpty(ext.codeBeforeMethod) && isEmpty(ext.codeAfterMethod))) {
            println("plugin disabled, copy all files")
            TransformUtils.copy(inputs, outputProvider)
            return
        }

        //将需要操作的 inputs 加入 Javassist 的 ClassPool 路径中

        ClassPool pool = ClassPool.getDefault()
        TransformUtils.appendClassPathToPool(pool, target, inputs)

        outputProvider.deleteAll()
        // Transform 输出的目标地址
        File outDir = outputProvider.getContentLocation("jackwaiting", outputTypes, scopes, Format.DIRECTORY)
        println("plugin enter" +ext )
        TransformUtils.traversalClasses(inputs, new TransformUtils.Callback() {
            @Override
            void process(String className) {
                addCtClass( pool, className)
            }
        })
        //TransformUtils.copy(inputs, outputProvider)
        startThreadPool(classes,outDir)
    }

    private void  startThreadPool(ArrayList<CtClass> classes,File outDir){
        new ForkJoinPool().submit {
            classes.each { ctClass ->
                processClass(ctClass, outDir.absolutePath)
            }
        }.get()
    }

    private void addCtClass(ClassPool pool, String className) {
        try {
//            LogUtils.debug("add class '${className}'")
            CtClass c = pool.getCtClass(className)
            classes.add(c)
        } catch (NotFoundException e) {
            LogUtils.error("can not find class '${className}'!\n${e.getMessage()}")
        }
    }

    private void processClass(CtClass ctClass, String outDir) {
        try {
            if (!JavassistUtils.acceptCtClass(ctClass,ext)) {
                if (ext.enableClassLog) {
                    println("ignore class '${ctClass.name}'")
                }
                ctClass.writeFile(outDir)
                ctClass.detach()
                return
            }
            if (ext.enableClassLog) {
                println("process class '${ctClass.name}'")
            }
            if (ctClass.isFrozen()) {
                ctClass.defrost()
            }
            // 所有方法和构造函数
            ctClass.declaredBehaviors.findAll { CtBehavior behavior ->
                return JavassistUtils.acceptCtBehavior(behavior,ext)
            }.each { CtBehavior method ->
                if (ext.enableClassLog && ext.enableMethodLog) {
                    println("\tprocess method '${method.name}'")
                }
                String before = replaceVar(ext.codeBeforeMethod, ctClass, method)
                String after = replaceVar(ext.codeAfterMethod, ctClass, method)
                if (!isEmpty(before)) {
                    method.addLocalVariable(ext.codeLocalVariable, transformByteCode(ext.codeLocalVariableType))
                    method.insertBefore(before)
                }
                if (!isEmpty(after)) {
                    method.insertAfter(after)
                }
            }
            ctClass.writeFile(outDir)
            ctClass.detach()
        } catch (CannotCompileException e) {
            LogUtils.error("can not compile code ! \n${e.getMessage()}")
            ctClass.writeFile(outDir)
            ctClass.detach()
        } catch (Exception e) {
            LogUtils.error("process class '${ctClass.name}' failed!")
            if (ext.enableStackLog) {
                e.printStackTrace()
            }
            ctClass.writeFile(outDir)
            ctClass.detach()
        }
    }

    /**
     * 简单变量替换处理
     * */
    private static String replaceVar(String s, CtClass c, CtBehavior m) {
        if (s == null || s.length() == 0) {
            return null
        }
        return s.replace(Constants.VAR_CLASS_NAME, c.name)
                .replace(Constants.VAR_SIMPLE_CLASS_NAME, c.simpleName)
                .replace(Constants.VAR_METHOD_NAME, m.name)
    }

    private static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0
    }


    /**
     * 简单变量转换处理
     * */
    private static CtClass transformByteCode(String byteCodeType) {

        switch (byteCodeType){
            case ByteCode.BYTE:
                return CtClass.byteType
            case ByteCode.SHORT:
                return CtClass.shortType
            case ByteCode.INT:
                return CtClass.intType
            case ByteCode.LONG:
                return CtClass.longType
            case ByteCode.BOOLEAN:
                return CtClass.booleanType
            case ByteCode.CHAR:
                return CtClass.charType
            case ByteCode.FLOAT:
                return CtClass.floatType
            case ByteCode.DOUBLE:
                return CtClass.doubleType
        }
    }
}