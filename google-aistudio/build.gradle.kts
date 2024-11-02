plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit")
    id("com.github.johnrengelman.shadow")
}

var pckg = rootProject.ext.get("pckg")

description = "ServerAssistantAI addon for Google AI Studio model support."

bukkit {
    name = "SAAI-GoogleAIStudio"
    main = "$pckg.aistudio.GoogleAiStudioAddon"
}

dependencies {
    implementation(libs.langchain4j.googleai) {
        isTransitive = false
    }
    bukkitLibrary(libs.retrofit2.gson)
}

tasks.shadowJar {
    relocate("dev.langchain4j.model.googleai", "$pckg.dependencies.langchain4j.googleai")
}