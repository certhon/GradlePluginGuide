package com.example.harry

import org.gradle.api.Plugin
import org.gradle.api.Project

class HarryPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.create("harryTask"){
            print(this.name)
        }
    }
}