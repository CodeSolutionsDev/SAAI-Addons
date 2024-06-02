plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit")
    id("com.github.johnrengelman.shadow")
}

var pckg = rootProject.ext.get("pckg")

description = "ServerAssistantAI addon for Mistral AI support."

bukkit {
    name = "SAAI-MistralAI"
    main = "$pckg.mistralai.MistralAiAddon"
}

dependencies {
    implementation(libs.langchain4j.mistralai) {
        isTransitive = false
    }
}

tasks.shadowJar {
    relocate("dev.langchain4j.model.mistralai", "$pckg.dependencies.langchain4j.mistralai")
}