plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit")
    id("com.gradleup.shadow")
}

var pckg = rootProject.ext.get("pckg")

description = "ServerAssistantAI addon for Anthropic model support."

bukkit {
    name = "SAAI-Anthropic"
    main = "$pckg.anthropic.AnthropicAddon"
}

dependencies {
    implementation(libs.langchain4j.anthropic) {
        isTransitive = false
    }
}

tasks.shadowJar {
    relocate("dev.langchain4j.model.anthropic", "$pckg.dependencies.langchain4j.anthropic")
}