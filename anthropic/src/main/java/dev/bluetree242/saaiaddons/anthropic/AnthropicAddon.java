package dev.bluetree242.saaiaddons.anthropic;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class AnthropicAddon extends JavaPlugin {
    public static final String NAME = "anthropic";

    @Override
    public void onLoad() {
        RegisteredServiceProvider<ServerAssistantAIAPI> registration = getServer().getServicesManager().getRegistration(ServerAssistantAIAPI.class);
        if (registration == null) {
            throw new IllegalStateException("Registered service provider is null");
        }
        ServerAssistantAIAPI api = registration.getProvider();
        api.getCredentialsRegistry().register(NAME, new AnthropicCredentialsLoader());
        api.getChatModelRegistry().register(NAME, new AnthropicChatModelProvider(api));
    }
}
