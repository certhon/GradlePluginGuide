package com.example.myplugin
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.logging.Logging

fun baseExtension(project: Project) = project.extensions.getByName("android") as BaseExtension

fun appExtension(project: Project) = project.extensions.getByName("android") as? AppExtension

fun customExtension(project: Project) = project.extensions.getByName("HarryOptions") as HarryOptions

fun uploadArchivesTask(project: Project): Task? = project.tasks.getByName("uploadArchives")

val logger get() = Logging.getLogger("harry")