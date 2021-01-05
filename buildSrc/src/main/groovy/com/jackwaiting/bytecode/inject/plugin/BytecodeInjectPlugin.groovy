package com.jackwaiting.bytecode.inject.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class BytecodeInjectPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        def extension = target.extensions.create(Constants.EXTENSIONS_NAME, BytecodeInjectExtension)

        println("plugin applied")
        target.afterEvaluate {
            println("plugin  afterEvaluate applied")
            if (extension.enable) {

            }
        }

        println("plugin registerTransform ")
        def android = target.extensions.getByType(BaseExtension)
        android.registerTransform(new BytecodeInjectTransform(target))

    }
}