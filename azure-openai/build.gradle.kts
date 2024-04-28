plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit")
    id("com.github.johnrengelman.shadow")
}

var pckg = rootProject.ext.get("pckg")

description = "ServerAssistantAI addon for Azure OpenAI model support."

bukkit {
    name = "SAAI-AzureOpenAI"
    main = "$pckg.azure.AzureOpenAIAddon"
}

dependencies {
    implementation(libs.langchain4j.azureopenai) {
        isTransitive = false
    }
}

tasks.shadowJar {
    relocate("dev.langchain4j.model.azure", "$pckg.dependencies.langchain4j.azure")
}