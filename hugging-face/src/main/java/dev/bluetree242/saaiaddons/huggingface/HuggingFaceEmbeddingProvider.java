package dev.bluetree242.saaiaddons.huggingface;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.config.option.OptionMap;
import dev.bluetree242.serverassistantai.api.registry.embedding.EmbeddingContext;
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
    public HuggingFaceEmbeddingModel provide(@NotNull EmbeddingContext context) {
        OptionMap options = context.options();
        String accessToken = api.getCredentialsRegistry().getConfigured(HuggingFaceAddon.NAME, HuggingFaceCredentialsLoader.class);
        String model = options.getString("model");
        if (model.isBlank()) throw new IllegalStateException("Please set the model for hugging face embedding model.");
        return HuggingFaceEmbeddingModel.builder().accessToken(accessToken)
                .modelId(model)
                .timeout(Duration.ofSeconds(options.getLong("timeout")))
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
    public OptionMap export(@NotNull EmbeddingContext context) {
        OptionMap result = EmbeddingModelProvider.super.export(context);
        result.putIfAbsent("model", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable EmbeddingContext context) {
        return "HuggingFace";
    }
}