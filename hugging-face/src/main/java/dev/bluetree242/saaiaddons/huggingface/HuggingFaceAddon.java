package dev.bluetree242.saaiaddons.huggingface;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class HuggingFaceAddon extends JavaPlugin {
    public static final String NAME = "huggingface";

    @Override
    public void onLoad() {
        RegisteredServiceProvider<ServerAssistantAIAPI> registration = getServer().getServicesManager().getRegistration(ServerAssistantAIAPI.class);
        if (registration == null) {
            throw new IllegalStateException("Registered service provider is null");
        }
        ServerAssistantAIAPI api = registration.getProvider();
        api.getCredentialsRegistry().register(NAME, new HuggingFaceCredentialsLoader());
        api.getChatModelRegistry().register(NAME, new HuggingFaceChatModelProvider(api));
        api.getEmbeddingModelRegistry().register(NAME, new HuggingFaceEmbeddingProvider(api));
    }

}
