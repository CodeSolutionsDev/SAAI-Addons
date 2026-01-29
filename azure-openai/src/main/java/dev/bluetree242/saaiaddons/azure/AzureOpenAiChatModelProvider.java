package dev.bluetree242.saaiaddons.azure;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.config.option.OptionMap;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelContext;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelProvider;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
public class AzureOpenAiChatModelProvider implements ChatModelProvider<AzureOpenAiChatModel> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public AzureOpenAiChatModel provide(@NotNull ChatModelContext context) {
        OptionMap options = context.options();
        AzureOpenAiCredentials credentials = api.getCredentialsRegistry().getConfigured(AzureOpenAiAddon.NAME, AzureOpenAiCredentials.Loader.class);
        if (credentials == null)
            throw new IllegalStateException("Azure OpenAI credentials is null. This is unexpected behavior.");
        String deploymentName = options.getString("deployment_name");
        if (deploymentName.isBlank())
            throw new IllegalStateException("Please set the deployment name (model) for azure openai chat.");
        return AzureOpenAiChatModel.builder()
                .apiKey(credentials.apiKey())
                .deploymentName(deploymentName)
                .serviceVersion(credentials.serviceVersion())
                .endpoint(credentials.endpoint())
                .timeout(Duration.ofSeconds(options.getLong("timeout")))
                .maxTokens(options.getIntegerOrDefault("max_tokens", i -> i == 0, null))
                .stop(options.getList("stop").getStringList())
                .maxRetries(options.getInteger("max_retries"))
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
    public OptionMap export(@NotNull ChatModelContext context) {
        OptionMap result = ChatModelProvider.super.export(context);
        // Makes sure the "deployment_name" is always in the config even if it is not configured.
        result.putIfAbsent("deployment_name", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable ChatModelContext context) {
        return "Azure OpenAI";
    }
}