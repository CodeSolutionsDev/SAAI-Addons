package dev.bluetree242.saaiaddons.azure;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class AzureOpenAiAddon extends JavaPlugin {
    public static final String NAME = "azure-openai";
    private ServerAssistantAIAPI api;

    @Override
    public void onLoad() {
        RegisteredServiceProvider<ServerAssistantAIAPI> registration = getServer().getServicesManager().getRegistration(ServerAssistantAIAPI.class);
        if (registration == null) {
            throw new IllegalStateException("Registered service provider is null");
        }
        api = registration.getProvider();
        api.waitForPlugin(this);
    }

    @Override
    public void onEnable() {
        api.getCredentialsRegistry().register(NAME, new AzureOpenAiCredentials.Loader());
        api.getChatModelRegistry().register(NAME, new AzureOpenAiChatModelProvider(api));
        api.getEmbeddingModelRegistry().register(NAME, new AzureOpenAiEmbeddingProvider(api));
    }
}
