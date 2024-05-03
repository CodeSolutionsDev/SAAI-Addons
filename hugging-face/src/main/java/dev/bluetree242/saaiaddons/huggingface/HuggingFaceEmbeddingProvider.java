package dev.bluetree242.saaiaddons.huggingface;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.registry.embedding.EmbeddingModelProvider;
import dev.langchain4j.model.huggingface.HuggingFaceEmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
public class HuggingFaceEmbeddingProvider implements EmbeddingModelProvider<HuggingFaceEmbeddingModel> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public HuggingFaceEmbeddingModel provide(@NotNull Map<String, Object> options) {
        String accessToken = api.getCredentialsRegistry().getConfigured(HuggingFaceAddon.NAME, HuggingFaceCredentialsLoader.class);
        String model = (String) options.get("model");
        if (model.isBlank()) throw new IllegalStateException("Please set the model for hugging face embedding model.");
        return HuggingFaceEmbeddingModel.builder().accessToken(accessToken)
                .modelId(model)
                .timeout(Duration.ofSeconds(Long.parseLong(options.get("timeout").toString())))
                .waitForModel(true)
                .build();
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of(
                "model", "",
                "timeout", 120L
        );
    }

    @NotNull
    @Override
    public Map<String, Object> export(@NotNull Map<String, Object> options) {
        Map<String, Object> result = EmbeddingModelProvider.super.export(options);
        result.putIfAbsent("model", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable Map<String, Object> options) {
        return "HuggingFace";
    }
}
