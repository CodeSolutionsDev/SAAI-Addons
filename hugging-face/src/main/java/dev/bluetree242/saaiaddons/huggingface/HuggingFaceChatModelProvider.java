package dev.bluetree242.saaiaddons.huggingface;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.config.option.OptionMap;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelContext;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelProvider;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
public class HuggingFaceChatModelProvider implements ChatModelProvider<HuggingFaceChatModel> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public HuggingFaceChatModel provide(@NotNull ChatModelContext context) {
        OptionMap options = context.options();
        String accessToken = api.getCredentialsRegistry().getConfigured(HuggingFaceAddon.NAME, HuggingFaceCredentialsLoader.class);
        String model = options.getString("model");
        if (model.isBlank()) throw new IllegalStateException("Please set the model for hugging face chat model.");
        return new HuggingFaceChatModel.Builder().accessToken(accessToken)
                .modelId(model)
                .timeout(Duration.ofSeconds(options.getLong("timeout")))
                .waitForModel(true)
                .returnFullText(false)
                .maxNewTokens(options.getIntegerOrDefault("max_new_tokens", i -> i == 0, null))
                .temperature(options.getDoubleOrDefault("temperature", i -> i == 0, null))
                .build();
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of(
                "model", "",
                "timeout", 15L,
                "max_new_tokens", 0,
                "temperature", 0
        );
    }

    @NotNull
    @Override
    public OptionMap export(@NotNull ChatModelContext context) {
        OptionMap result = ChatModelProvider.super.export(context);
        result.putIfAbsent("model", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable ChatModelContext context) {
        return "HuggingFace";
    }
}