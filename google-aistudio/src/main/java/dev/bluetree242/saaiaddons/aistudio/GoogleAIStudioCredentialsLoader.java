package dev.bluetree242.saaiaddons.aistudio;

import dev.bluetree242.serverassistantai.api.exceptions.MissingCredentialsException;
import dev.bluetree242.serverassistantai.api.registry.credentials.CredentialsLoader;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class GoogleAIStudioCredentialsLoader implements CredentialsLoader<String> {

    @NotNull
    @Override
    public String load(@NotNull Map<String, Object> options) {
        String apiKey = (String) options.get("api_key");
        if (apiKey.isBlank()) throw new MissingCredentialsException("Google AI Studio api key");
        return apiKey;
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of("api_key", "");
    }
}
