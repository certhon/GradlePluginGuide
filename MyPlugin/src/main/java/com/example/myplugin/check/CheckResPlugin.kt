package com.example.myplugin.check
import com.example.myplugin.baseExtension
import com.example.myplugin.customExtension
import com.example.myplugin.logger
import com.example.myplugin.uploadArchivesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.logging.LogLevel
import java.io.File


class CheckResPlugin : Plugin<Project> {


    override fun apply(project: Project) {

        val resTask = createResTask(project)
        project.afterEvaluate {
            uploadArchivesTask(project)?.setDependsOn(listOf(resTask))
        }
    }

    private fun createResTask(project: Project): Task? {
        val options = customExtension(project)
        return project.tasks.create("checkRes") { task ->
            task.group = "harry"
            task.doFirst {

                println("----------------------------------")
                println("检测大于${options.resErrorKb}KB的资源...")
                baseExtension(project)
                        .sourceSets.flatMap {
                            it.res.srcDirs + it.assets.srcDirs
                        }
                        .flatMap {
                            findOverLimitFile(options.resErrorKb, it)
                        }
                        .onEach {
                            logger.log(LogLevel.ERROR, it.toString())
                            logger.log(LogLevel.ERROR, " file size:" + it.length().smartSize())
                        }
                        .also {
                            if (it.isNotEmpty()) {
//                                it.forEach {
//                                    println(it.name)
//                                }
//                                println("检测到大于${options.resErrorKb}KB的资源")

                            }
                        }
            }
        }
    }


    //查找超过限制文件
    private fun findOverLimitFile(limitInKB: Int, root: File): Set<File> {
        return root.walk()
                .filter { file ->
                    file.extension in listOf("so", "png", "jpg", "jpeg", "ico", "svg", "webp", "gif")
                }
                .filter { file ->
                    file.length() > limitInKB * 1024
                }.toSet()
    }

    //查找非webp图片资源

    private fun Long.smartSize(): String {
        val mb = this / 1000f / 1000f
        if (mb >= 1) {
            return String.format("%.2fMB", mb)
        }

        val kb = this / 1000f
        if (kb >= 1) {
            return String.format("%.2fKB", kb)
        }
        return this.toString() + "B"
    }

}