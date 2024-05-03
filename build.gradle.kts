import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0" apply false
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

var pckg = "dev.bluetree242.saaiaddons"
ext {
    extra.apply {
        set("pckg", pckg)
    }
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
        maven("https://repo.bluetree242.dev/public")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    dependencies {
        compileOnly(rootProject.libs.serverassistantai) {
            exclude("com.discordsrv")
        }
        compileOnly(rootProject.libs.spigot)
        compileOnly(rootProject.libs.lombok)
        annotationProcessor(rootProject.libs.lombok)
    }

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    tasks.compileJava {
        options.encoding = "UTF-8"
    }

    tasks.jar {
        manifest {
            attributes("paperweight-mappings-namespace" to "mojang")
        }
    }
}

gradle.projectsEvaluated {
    subprojects {
        if (plugins.hasPlugin("net.minecrell.plugin-yml.bukkit")) {
            val bukkit = extensions.getByType(BukkitPluginDescription::class.java)
            bukkit.apply {
                depend = listOf("ServerAssistantAI")
                website = "code-solutions.dev"
                author = "BlueTree242"
                foliaSupported = true
                apiVersion = "1.16"
                version = rootProject.version.toString()
                if (description == null) description = project.description
            }
            tasks.jar {
                archiveBaseName = bukkit.name
            }
        }
        tasks.build {
            finalizedBy(tasks.withType(ShadowJar::class.java))
        }
        if (plugins.hasPlugin("com.github.johnrengelman.shadow")) {
            tasks.withType(ShadowJar::class.java) {
                finalizedBy(copyBuildOutput)
                archiveClassifier.set("")
                minimize()
                archiveBaseName = tasks.jar.get().archiveBaseName
                manifest {
                    from(tasks.jar.get().manifest)
                }
            }
        } else {
            tasks.build.get().finalizedBy(copyBuildOutput)
        }
    }
}

val outputDir = file("out")

val copyBuildOutput by tasks.registering {
    dependsOn(subprojects.map { it.tasks.build.get() })

    doLast {
        subprojects.forEach { sp ->
            val buildLibsDir = sp.layout.buildDirectory.get().dir("libs").asFile
            if (buildLibsDir.exists()) {
                project.copy {
                    from(buildLibsDir)
                    into(outputDir)
                }
            }
        }
    }
}

tasks.clean {
    doLast {
        outputDir.deleteRecursively()
    }
}
