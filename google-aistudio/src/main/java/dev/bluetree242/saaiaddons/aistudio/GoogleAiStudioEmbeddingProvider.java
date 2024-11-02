package dev.bluetree242.saaiaddons.aistudio;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.config.option.OptionMap;
import dev.bluetree242.serverassistantai.api.registry.embedding.EmbeddingContext;
import dev.bluetree242.serverassistantai.api.registry.embedding.EmbeddingModelProvider;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
public class GoogleAiStudioEmbeddingProvider implements EmbeddingModelProvider<GoogleAiEmbeddingModel> {
    private final ServerAssistantAIAPI api;

    @Override
    public @NotNull GoogleAiEmbeddingModel provide(@NotNull EmbeddingContext context) {
        OptionMap options = context.options();
        String apiKey = api.getCredentialsRegistry().getConfigured(GoogleAiStudioAddon.NAME, GoogleAiStudioCredentialsLoader.class);
        String model = options.getString("model");
        if (model.isBlank())
            throw new IllegalStateException("Please set the model for Google AI Studio embedding model.");
        return GoogleAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName(model)
                .timeout(Duration.ofSeconds(options.getLong("timeout")))
                .build();
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of(
                "model", "",
                "timeout", 15L
        );
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable EmbeddingContext context) {
        return "Google AI Studio";
    }
}
