package dev.bluetree242.saaiaddons.aistudio;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class GoogleAiStudioAddon extends JavaPlugin {
    public static final String NAME = "google-aistudio";

    @Override
    public void onLoad() {
        RegisteredServiceProvider<ServerAssistantAIAPI> registration = getServer().getServicesManager().getRegistration(ServerAssistantAIAPI.class);
        if (registration == null) {
            throw new IllegalStateException("Registered service provider is null");
        }
        ServerAssistantAIAPI api = registration.getProvider();
        api.getCredentialsRegistry().register(NAME, new GoogleAiStudioCredentialsLoader());
        api.getChatModelRegistry().register(NAME, new GoogleAiStudioChatModelProvider(api));
        api.getEmbeddingModelRegistry().register(NAME, new GoogleAiStudioEmbeddingProvider(api));
    }
}
