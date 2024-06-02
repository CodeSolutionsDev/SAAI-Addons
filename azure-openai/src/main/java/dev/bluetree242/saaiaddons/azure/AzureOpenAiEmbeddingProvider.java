package dev.bluetree242.saaiaddons.azure;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.registry.embedding.EmbeddingModelProvider;
import dev.langchain4j.model.azure.AzureOpenAiEmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
public class AzureOpenAiEmbeddingProvider implements EmbeddingModelProvider<AzureOpenAiEmbeddingModel> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public AzureOpenAiEmbeddingModel provide(@NotNull Map<String, Object> options) {
        AzureOpenAiCredentials credentials = api.getCredentialsRegistry().getConfigured(AzureOpenAiAddon.NAME, AzureOpenAiCredentials.Loader.class);
        if (credentials == null)
            throw new IllegalStateException("Azure OpenAI credentials is null. This is unexpected behavior.");
        String deploymentName = (String) options.get("deployment_name");
        if (deploymentName.isBlank())
            throw new IllegalStateException("Please set the deployment name (model) for azure openai chat.");
        return AzureOpenAiEmbeddingModel.builder()
                .apiKey(credentials.apiKey())
                .deploymentName(deploymentName)
                .serviceVersion(credentials.serviceVersion())
                .endpoint(credentials.endpoint())
                .timeout(Duration.ofSeconds(Long.parseLong(options.get("timeout").toString())))
                .maxRetries((Integer) options.get("max_retries"))
                .build();
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of(
                "deployment_name", "",
                "timeout", 15L,
                "max_retries", 3
        );
    }

    @NotNull
    @Override
    public Map<String, Object> export(@NotNull Map<String, Object> options) {
        Map<String, Object> result = EmbeddingModelProvider.super.export(options);
        // Makes sure the "deployment_name", "service_version" and "endpoint" are always in the config even if it is not configured.
        result.putIfAbsent("deployment_name", "");
        result.putIfAbsent("service_version", "");
        result.putIfAbsent("endpoint", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable Map<String, Object> options) {
        return "Azure OpenAI";
    }
}
