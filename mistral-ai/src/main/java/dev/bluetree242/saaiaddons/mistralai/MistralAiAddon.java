package dev.bluetree242.saaiaddons.mistralai;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MistralAiAddon extends JavaPlugin {
    public static final String NAME = "mistralai";

    @Override
    public void onLoad() {
        RegisteredServiceProvider<ServerAssistantAIAPI> registration = getServer().getServicesManager().getRegistration(ServerAssistantAIAPI.class);
        if (registration == null) {
            throw new IllegalStateException("Registered service provider is null");
        }
        ServerAssistantAIAPI api = registration.getProvider();
        api.getCredentialsRegistry().register(NAME, new MistralAiCredentialsLoader());
        api.getChatModelRegistry().register(NAME, new MistralAiChatModelProvider(api));
        api.getEmbeddingModelRegistry().register(NAME, new MistralAiEmbeddingProvider(api));
    }
}
