package com.example.myplugin.check

import org.gradle.api.Plugin
import org.gradle.api.Project


class CheckAllPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val checkAllTask = project.tasks.create("checkAll") {
            it.group = "harry"
        }
        project.afterEvaluate {
            checkAllTask.setDependsOn(listOf(project.tasks.filter { it.group == "harry" && it.name != "checkAll" }))
        }
    }

}