package dev.bluetree242.saaiaddons.azure;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.config.option.OptionMap;
import dev.bluetree242.serverassistantai.api.registry.embedding.EmbeddingContext;
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
    public AzureOpenAiEmbeddingModel provide(@NotNull EmbeddingContext context) {
        OptionMap options = context.options();
        AzureOpenAiCredentials credentials = api.getCredentialsRegistry().getConfigured(AzureOpenAiAddon.NAME, AzureOpenAiCredentials.Loader.class);
        if (credentials == null)
            throw new IllegalStateException("Azure OpenAI credentials is null. This is unexpected behavior.");
        String deploymentName = options.getString("deployment_name");
        if (deploymentName.isBlank())
            throw new IllegalStateException("Please set the deployment name (model) for azure openai chat.");
        return AzureOpenAiEmbeddingModel.builder()
                .apiKey(credentials.apiKey())
                .deploymentName(deploymentName)
                .serviceVersion(credentials.serviceVersion())
                .endpoint(credentials.endpoint())
                .timeout(Duration.ofSeconds(options.getLong("timeout")))
                .maxRetries(options.getInteger("max_retries"))
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
    public Map<String, Object> export(@NotNull EmbeddingContext context) {
        Map<String, Object> result = EmbeddingModelProvider.super.export(context);
        // Makes sure the "deployment_name" is always in the config even if it is not configured.
        result.putIfAbsent("deployment_name", "");
        result.putIfAbsent("service_version", "");
        result.putIfAbsent("endpoint", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable EmbeddingContext context) {
        return "Azure OpenAI";
    }
}
