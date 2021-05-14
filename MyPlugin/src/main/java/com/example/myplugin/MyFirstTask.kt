package com.example.myplugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


open class MyFirstTask : DefaultTask(){

    @Input
    var inputName:String? = null
    @OutputFile
    var file: File? = null
    @TaskAction
    fun generateFile(){
        file?.apply {
            writeText("Hello $inputName!\nPrinted at: ${SimpleDateFormat("HH:mm:ss").format(Date())}")
        }
    }
}
