package dev.bluetree242.saaiaddons.mistralai;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.config.option.OptionMap;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelContext;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelProvider;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
public class MistralAiChatModelProvider implements ChatModelProvider<MistralAiChatModel> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public MistralAiChatModel provide(@NotNull ChatModelContext context) {
        OptionMap options = context.options();
        String model = options.getString("model");
        if (model.isBlank()) throw new IllegalStateException("Please set the model for MistralAI chat model.");
        return MistralAiChatModel.builder()
                .modelName(model)
                .baseUrl(options.getString("base_url"))
                .timeout(Duration.ofSeconds(options.getLong("timeout")))
                .maxTokens(options.getIntegerOrDefault("max_tokens", i -> i == 0, null))
                .maxRetries(options.getInteger("max_retries"))
                .apiKey(api.getCredentialsRegistry().getConfigured(MistralAiAddon.NAME, MistralAiCredentialsLoader.class))
                .build();
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of(
                "base_url", "https://api.mistral.ai/v1",
                "model", "",
                "timeout", 15L,
                "max_tokens", 0,
                "max_retries", 3
        );
    }

    @NotNull
    @Override
    public Map<String, Object> export(@NotNull ChatModelContext context) {
        Map<String, Object> result = ChatModelProvider.super.export(context);
        // Makes sure the "model" is always in the config even if it is not configured.
        result.putIfAbsent("model", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable ChatModelContext context) {
        return "MistralAI";
    }
}
