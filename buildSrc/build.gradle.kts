plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}

gradlePlugin {
    plugins {
        create("HarryPlugin2") {
            //插件id,用于引用插件,如：apply plugin: 'id'
            id = "com.example.harry2"
            //指定插件的实现类
            implementationClass = "com.example.harry.HarryPlugin2"
        }
    }
}

