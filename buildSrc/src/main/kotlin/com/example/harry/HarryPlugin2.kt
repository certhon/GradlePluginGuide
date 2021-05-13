package com.example.harry

import org.gradle.api.Plugin
import org.gradle.api.Project

class HarryPlugin2 : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.create("harryTask2"){
            print(this.name)
        }
    }
}