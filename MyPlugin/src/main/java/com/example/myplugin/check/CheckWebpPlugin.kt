package com.example.myplugin.check
import com.example.myplugin.baseExtension
import com.example.myplugin.logger
import com.example.myplugin.uploadArchivesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.logging.LogLevel
import java.io.File


class CheckWebpPlugin : Plugin<Project> {


    override fun apply(project: Project) {


        val webpTask = createWebpTask(project)

        project.afterEvaluate {
            uploadArchivesTask(project)?.setDependsOn(listOf(webpTask))
        }
    }

    private fun createWebpTask(project: Project): Task? {
        return project.tasks.create("checkWebp") { task ->
            task.group = "harry"
            task.doFirst {
                println("----------------------------------")
                println("检测非webp资源...")

                baseExtension(project)
                        .sourceSets.flatMap {
                            it.res.srcDirs + it.assets.srcDirs
                        }
                        .flatMap {
                            findNotWebpResFiles(it)
                        }
                        .onEach {
                            logger.log(LogLevel.ERROR, it.toString())
                        }
                        .also {
                            if (it.isNotEmpty()) {
//                                throw RuntimeException("检测到非webp资源")
                            }
                        }
            }
        }
    }

    //查找非webp图片资源
    private fun findNotWebpResFiles(root: File): Set<File> {
        return root.walk()
                .filter { file ->
                    file.extension in listOf("png", "jpg", "jpeg")
                }
                .filter { file ->
                    file.name.endsWith(".9.png").not()
                }
                .toSet()
    }

}