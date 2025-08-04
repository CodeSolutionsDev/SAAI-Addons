plugins {
    id("java")
    id("net.minecrell.plugin-yml.bukkit")
    id("com.gradleup.shadow")
}

var pckg = rootProject.ext.get("pckg")

description = "ServerAssistantAI addon for HuggingFace model support."

bukkit {
    name = "SAAI-HuggingFace"
    main = "$pckg.huggingface.HuggingFaceAddon"
}

dependencies {
    implementation(libs.langchain4j.huggingface) {
        isTransitive = false
    }
}

tasks.shadowJar {
    relocate("dev.langchain4j.model.huggingface", "$pckg.dependencies.langchain4j.huggingface")
}