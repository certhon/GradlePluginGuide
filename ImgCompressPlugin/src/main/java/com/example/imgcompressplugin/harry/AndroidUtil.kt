package com.example.imgcompressplugin.harry;
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidUtil {

    companion object {
        fun getMinSdkVersion(project: Project): Int {
            return (project.property("android") as BaseExtension).defaultConfig.minSdkVersion.apiLevel
        }
    }

}
