package com.example.myplugin
import com.android.build.gradle.tasks.ManifestProcessorTask
import groovy.util.XmlSlurper
import groovy.util.slurpersupport.NodeChild
import groovy.util.slurpersupport.NodeChildren
import groovy.xml.XmlUtil
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File


@Suppress("UnstableApiUsage")
class ActivityExportPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.afterEvaluate {
            appExtension(it)?.applicationVariants?.all { variant ->
                variant.outputs.onEach { output ->
                    output.processManifestProvider.get().doLast { task ->
                        val manifestFile = File((task as ManifestProcessorTask).manifestOutputDirectory.get().asFile, "AndroidManifest.xml")
                        if (manifestFile.exists()) {
                            replaceManifest(manifestFile)
                        }
                    }
                }
            }
        }
    }

    private fun replaceManifest(manifestFile: File) {
        val xmlSlurper = XmlSlurper(false, false)

        println("----------------------------------")
        println("读取 manifest...")

        val manifest = manifestFile.readText().replace("\uFEFF", "")
        val result = xmlSlurper.parseText(manifest)
        val application = result.getProperty("application") as? NodeChildren
        val activity = application?.getProperty("activity") as? NodeChildren
        activity?.forEach { act ->
            val attributes = (act as? NodeChild)?.attributes()
            attributes?.put("android:exported", "true")
        }
        val newManifest = XmlUtil.serialize(result)
        manifestFile.setWritable(true)
        manifestFile.writeText(newManifest)

        println("替换 manifest...")
    }
}