package dev.bluetree242.saaiaddons.anthropic;

import dev.bluetree242.serverassistantai.api.exceptions.MissingCredentialsException;
import dev.bluetree242.serverassistantai.api.registry.credentials.CredentialsContext;
import dev.bluetree242.serverassistantai.api.registry.credentials.CredentialsLoader;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class AnthropicCredentialsLoader implements CredentialsLoader<String> {
    @NotNull
    @Override
    public String load(@NotNull CredentialsContext context) {
        String apiKey = context.options().getString("api_key");
        if (apiKey.isBlank()) throw new MissingCredentialsException("Anthropic api key");
        return apiKey;
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of("api_key", "");
    }
}
