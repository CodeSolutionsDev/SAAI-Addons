package dev.bluetree242.saaiaddons.mistralai;

import dev.bluetree242.serverassistantai.api.exceptions.MissingCredentialsException;
import dev.bluetree242.serverassistantai.api.registry.credentials.CredentialsContext;
import dev.bluetree242.serverassistantai.api.registry.credentials.CredentialsLoader;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MistralAiCredentialsLoader implements CredentialsLoader<String> {
    @NotNull
    @Override
    public String load(@NotNull CredentialsContext context) {
        String apiKey = context.options().getString("api_key");
        if (apiKey.isBlank()) throw new MissingCredentialsException("MistralAI api key");
        return apiKey;
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of("api_key", "");
    }
}
