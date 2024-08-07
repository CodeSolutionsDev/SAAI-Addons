package dev.bluetree242.saaiaddons.huggingface;

import dev.bluetree242.serverassistantai.api.exceptions.MissingCredentialsException;
import dev.bluetree242.serverassistantai.api.registry.credentials.CredentialsContext;
import dev.bluetree242.serverassistantai.api.registry.credentials.CredentialsLoader;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class HuggingFaceCredentialsLoader implements CredentialsLoader<String> {

    @NotNull
    @Override
    public String load(@NotNull CredentialsContext context) {
        String accessToken = context.options().getString("access_token");
        if (accessToken.isBlank()) throw new MissingCredentialsException("HuggingFace access token");
        return accessToken;
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of("access_token", "");
    }
}
