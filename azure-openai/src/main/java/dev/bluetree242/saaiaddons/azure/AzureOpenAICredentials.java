package dev.bluetree242.saaiaddons.azure;

import dev.bluetree242.serverassistantai.api.exceptions.MissingCredentialsException;
import dev.bluetree242.serverassistantai.api.registry.credentials.CredentialsLoader;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public record AzureOpenAICredentials(@NotNull String apiKey, @NotNull String serviceVersion, @NotNull String endpoint) {


    public static class Loader implements CredentialsLoader<AzureOpenAICredentials> {

        @NotNull
        @Override
        public AzureOpenAICredentials load(@NotNull Map<String, Object> options) {
            String apiKey = (String) options.get("api_key");
            if (apiKey.isBlank()) throw new MissingCredentialsException("Azure OpenAI api key");
            String serviceVersion = (String) options.get("service_version");
            String endpoint = (String) options.get("endpoint");
            if (serviceVersion.isBlank())
                throw new MissingCredentialsException("Azure OpenAI service version");
            if (endpoint.isBlank())
                throw new MissingCredentialsException("Azure OpenAI endpoint");
            return new AzureOpenAICredentials(apiKey, serviceVersion, endpoint);
        }

        @NotNull
        @Override
        public Map<String, Object> getDefaultOptions() {
            return Map.of(
                    "api_key", "",
                    "service_version", "",
                    "endpoint", ""
            );
        }
    }
}
