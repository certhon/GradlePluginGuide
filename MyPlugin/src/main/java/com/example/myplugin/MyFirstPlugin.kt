package com.example.myplugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File


class MyFirstPlugin:Plugin<Project>{
    override fun apply(project: Project) {
        //定义参数 在build.gradle设置参数 MyPluginParams{ name = "" }
        project.extensions.create("MyPluginParams",MyPluginParams::class.java)
        //获取参数
//        var name = project.extensions.findByType(MyPluginParams::class.java)?.name
        //创建task生成文件
        /*
        project.tasks.create("MyFirstPlugin"){
            it.group = "MyPluginTasks"
            it.doLast {
                File("${project.projectDir.path}/myFirstGeneratedFile.txt").apply {
                    writeText("Hello $name!\nPrinted at: ${SimpleDateFormat("HH:mm:ss").format(Date())}")
                }
            }
        }*/
        //注册MyFirstTask
        project.tasks.register("MyFirstPlugin", MyFirstTask::class.java) {
            it.group = "MyPluginTasks"
            //设置输入
            it.inputName = project.extensions.findByType(MyPluginParams::class.java)?.name
            //设置输出
            it.file = File("${project.projectDir.path}/myFirstGeneratedFile.txt")
        }
        //将MyFirstPlugin添加到构建树
        val android = project.extensions.findByType(BaseExtension::class.java)
        (android as  AppExtension).applicationVariants.all {
            //将MyFirstPlugin task添加到assemble task前
            //assemble依赖MyFirstPlugin的意思是说assemble运行前先运行MyFirstPlugin
            it.assembleProvider.dependsOn("MyFirstPlugin")
        }
    }
}