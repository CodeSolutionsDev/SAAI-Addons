package dev.bluetree242.saaiaddons.azure;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelProvider;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class AzureOpenAiChatModelProvider implements ChatModelProvider<AzureOpenAiChatModel> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public AzureOpenAiChatModel provide(@NotNull Map<String, Object> options) {
        AzureOpenAiCredentials credentials = api.getCredentialsRegistry().getConfigured(AzureOpenAiAddon.NAME, AzureOpenAiCredentials.Loader.class);
        if (credentials == null)
            throw new IllegalStateException("Azure OpenAI credentials is null. This is unexpected behavior.");
        Integer maxTokens = (Integer) options.get("max_tokens");
        String deploymentName = (String) options.get("deployment_name");
        //noinspection unchecked
        List<String> stop = (List<String>) options.get("stop");
        Integer maxRetries = (Integer) options.get("max_retries");
        if (deploymentName.isBlank())
            throw new IllegalStateException("Please set the deployment name (model) for azure openai chat.");
        return AzureOpenAiChatModel.builder()
                .apiKey(credentials.apiKey())
                .deploymentName(deploymentName)
                .serviceVersion(credentials.serviceVersion())
                .endpoint(credentials.endpoint())
                .timeout(Duration.ofSeconds(Long.parseLong(options.get("timeout").toString())))
                .maxTokens(maxTokens == 0 ? null : maxTokens)
                .stop(stop)
                .maxRetries(maxRetries)
                .build();
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of(
                "deployment_name", "",
                "timeout", 15L,
                "max_tokens", 0,
                "stop", Collections.emptyList(),
                "max_retries", 3
        );
    }

    @NotNull
    @Override
    public Map<String, Object> export(@NotNull Map<String, Object> options) {
        Map<String, Object> result = ChatModelProvider.super.export(options);
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
