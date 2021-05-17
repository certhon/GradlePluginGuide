package com.example.myplugin.check
import com.example.myplugin.baseExtension
import com.example.myplugin.uploadArchivesTask
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import java.io.FileInputStream


class CheckDuplicatePlugin : Plugin<Project> {

    override fun apply(project: Project) {

        val dupTask = checkDuplicateTask(project)

        project.afterEvaluate {
            uploadArchivesTask(project)?.setDependsOn(listOf(dupTask))
        }
    }

    private fun checkDuplicateTask(project: Project): Task? {
        return project.tasks.create("checkDuplicate") {
            it.group = "harry"
            it.doLast {
                println("----------------------------------")
                println("检测重复的资源...")

                baseExtension(project)
                        .sourceSets.flatMap {
                            it.res.srcDirs + it.assets.srcDirs
                        }
                        .flatMap { it.walk().filter { it.isFile }.toList() }
                        .map {
                            FileInfo(
                                it.name,
                                it.length(),
                                it.absolutePath,
                                DigestUtils.md5Hex(FileInputStream(it))
                            )
                        }
                        .groupBy {
                            it.md5
                        }
                        .filter {
                            it.value.size > 1
                        }
                        .onEach {
                            println(

                                    "md5:" + it.key + " size: " + it.value[0].length.smartSize()
                            )
                            it.value.forEach { fileInfo ->
                                println(fileInfo.path)
                            }
                            println( "\n")
                        }
                        .also {
                            if (it.isNotEmpty()) {
//                                throw RuntimeException("检测到重复的资源")
                            }
                        }
            }
        }
    }

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

    data class FileInfo(val name: String, val length: Long, val path: String, val md5: String)
}