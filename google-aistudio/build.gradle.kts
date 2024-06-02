plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit")
}

var pckg = rootProject.ext.get("pckg")

description = "ServerAssistantAI addon for Google AI Studio model support."

bukkit {
    name = "SAAI-GoogleAIStudio"
    main = "$pckg.aistudio.GoogleAiStudioAddon"
}

dependencies {
    compileOnly(libs.retrofit2)
}