package com.example.myplugin
import com.example.myplugin.check.CheckAllPlugin
import com.example.myplugin.check.CheckDuplicatePlugin
import com.example.myplugin.check.CheckResPlugin
import com.example.myplugin.check.CheckWebpPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project


class HarryCheckPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        project.extensions.create("HarryOptions", HarryOptions::class.java)

        listOf(
            CheckDuplicatePlugin(),
            CheckResPlugin(),
            CheckWebpPlugin(),
            CheckAllPlugin()
//                InstallLeakCanaryPlugin()
        )
                .forEach {
                    it.apply(project)
                }

    }

}