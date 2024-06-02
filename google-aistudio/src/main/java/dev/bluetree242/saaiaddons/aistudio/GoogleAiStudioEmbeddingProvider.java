package dev.bluetree242.saaiaddons.aistudio;

import dev.bluetree242.saaiaddons.aistudio.api.AiStudioEmbeddingModel;
import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.registry.embedding.EmbeddingModelProvider;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Map;
@RequiredArgsConstructor
public class GoogleAiStudioEmbeddingProvider implements EmbeddingModelProvider<AiStudioEmbeddingModel> {
    private final ServerAssistantAIAPI api;

    @Override
    public @NotNull AiStudioEmbeddingModel provide(@NotNull Map<String, Object> options) {
        String apiKey = api.getCredentialsRegistry().getConfigured(GoogleAiStudioAddon.NAME, GoogleAiStudioCredentialsLoader.class);
        String model = (String) options.get("model");
        if (model.isBlank()) throw new IllegalStateException("Please set the model for Google AI Studio embedding model.");
        return new AiStudioEmbeddingModel(apiKey, model, Duration.ofSeconds(Long.parseLong(options.get("timeout").toString())));
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
    public String getDisplayName(@Nullable Map<String, Object> options) {
        return "Google AI Studio";
    }
}
