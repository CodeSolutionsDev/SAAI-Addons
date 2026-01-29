package dev.bluetree242.saaiaddons.mistralai;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.config.option.OptionMap;
import dev.bluetree242.serverassistantai.api.registry.embedding.EmbeddingContext;
import dev.bluetree242.serverassistantai.api.registry.embedding.EmbeddingModelProvider;
import dev.langchain4j.model.mistralai.MistralAiEmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
public class MistralAiEmbeddingProvider implements EmbeddingModelProvider<MistralAiEmbeddingModel> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public MistralAiEmbeddingModel provide(@NotNull EmbeddingContext context) {
        OptionMap options = context.options();
        String model = options.getString("model");
        if (model.isBlank()) throw new IllegalStateException("Please set the model for MistralAI chat model.");
        return MistralAiEmbeddingModel.builder()
                .modelName(model)
                .baseUrl(options.getString("base_url"))
                .timeout(Duration.ofSeconds(options.getLong("timeout")))
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
                "max_retries", 3
        );
    }

    @NotNull
    @Override
    public OptionMap export(@NotNull EmbeddingContext context) {
        OptionMap result = EmbeddingModelProvider.super.export(context);
        // Makes sure the "model" is always in the config even if it is not configured.
        result.putIfAbsent("model", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable EmbeddingContext context) {
        return "MistralAI";
    }
}