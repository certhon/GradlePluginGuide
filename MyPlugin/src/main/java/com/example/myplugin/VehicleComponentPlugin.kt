package com.example.myplugin
import org.gradle.api.Plugin
import org.gradle.api.Project


class VehicleComponentPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        val debugEnv = !project.hasProperty("release")
        if (debugEnv) {
            ActivityExportPlugin().apply(project)
        }
    }
}