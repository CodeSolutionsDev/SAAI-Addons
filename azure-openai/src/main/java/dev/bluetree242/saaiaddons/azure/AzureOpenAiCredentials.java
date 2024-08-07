package dev.bluetree242.saaiaddons.azure;

import dev.bluetree242.serverassistantai.api.config.option.OptionMap;
import dev.bluetree242.serverassistantai.api.exceptions.MissingCredentialsException;
import dev.bluetree242.serverassistantai.api.registry.credentials.CredentialsContext;
import dev.bluetree242.serverassistantai.api.registry.credentials.CredentialsLoader;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public record AzureOpenAiCredentials(@NotNull String apiKey, @NotNull String serviceVersion, @NotNull String endpoint) {


    public static class Loader implements CredentialsLoader<AzureOpenAiCredentials> {

        @NotNull
        @Override
        public AzureOpenAiCredentials load(@NotNull CredentialsContext context) {
            OptionMap options = context.options();
            String apiKey = options.getString("api_key");
            String serviceVersion = options.getString("service_version");
            String endpoint = options.getString("endpoint");
            if (apiKey.isBlank())
                throw new MissingCredentialsException("Azure OpenAI api key");
            if (serviceVersion.isBlank())
                throw new MissingCredentialsException("Azure OpenAI service version");
            if (endpoint.isBlank())
                throw new MissingCredentialsException("Azure OpenAI endpoint");
            return new AzureOpenAiCredentials(apiKey, serviceVersion, endpoint);
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
