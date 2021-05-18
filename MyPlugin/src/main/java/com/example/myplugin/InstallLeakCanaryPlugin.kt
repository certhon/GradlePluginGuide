package com.example.myplugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class InstallLeakCanaryPlugin : Plugin<Project> {
    override fun apply(project: Project) {

//        project.afterEvaluate {
//            it.dependencies.add("debugImplementation", "com.squareup.leakcanary:leakcanary-android:2.4")
//        }

        if (customExtension(project).installLeakCanary) {
            project.dependencies.add("debugImplementation", "com.squareup.leakcanary:leakcanary-android:2.4")
        }

    }

}