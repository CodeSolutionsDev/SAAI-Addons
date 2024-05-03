package dev.bluetree242.saaiaddons.mistralai;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelProvider;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
public class MistralAIChatModelProvider implements ChatModelProvider<MistralAiChatModel> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public MistralAiChatModel provide(@NotNull Map<String, Object> options) {
        String model = (String) options.get("model");
        Integer maxTokens = (Integer) options.get("max_tokens");
        Integer maxRetries = (Integer) options.get("max_retries");
        if (model.isBlank()) throw new IllegalStateException("Please set the model for MistralAI chat model.");
        return MistralAiChatModel.builder()
                .modelName(model)
                .baseUrl((String) options.get("base_url"))
                .timeout(Duration.ofSeconds(Long.parseLong(options.get("timeout").toString())))
                .maxTokens(maxTokens == 0 ? null : maxTokens)
                .maxRetries(maxRetries)
                .apiKey(api.getCredentialsRegistry().getConfigured(MistralAIAddon.NAME, MistralAICredentialsLoader.class))
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
    public Map<String, Object> export(@NotNull Map<String, Object> options) {
        Map<String, Object> result = ChatModelProvider.super.export(options);
        // Makes sure the "model" is always in the config even if it is not configured.
        result.putIfAbsent("model", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable Map<String, Object> options) {
        return "MistralAI";
    }
}
