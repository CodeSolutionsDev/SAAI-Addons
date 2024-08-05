package dev.bluetree242.saaiaddons.aistudio;

import dev.bluetree242.saaiaddons.aistudio.api.AiStudioChatLanguageModel;
import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.config.option.OptionMap;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelContext;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelProvider;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
public class GoogleAiStudioChatModelProvider implements ChatModelProvider<AiStudioChatLanguageModel> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public AiStudioChatLanguageModel provide(@NotNull ChatModelContext context) {
        OptionMap options = context.options();
        String model = options.getString("model");
        //noinspection unchecked
        if (model.isBlank()) throw new IllegalStateException("Please set the model for Google AI Studio chat model.");
        return AiStudioChatLanguageModel.builder()
                .model(model)
                .timeout(Duration.ofSeconds(options.getLong("timeout")))
                .maxOutputTokens(options.getIntegerOrDefault("max_output_tokens", i -> i == 0, null))
                .stopSequences(options.getList("stop").getStringList())
                .apiKey(api.getCredentialsRegistry().getConfigured(GoogleAiStudioAddon.NAME, GoogleAiStudioCredentialsLoader.class))
                .build();
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of(
                "model", "",
                "timeout", 15L,
                "max_output_tokens", 0,
                "stop", Collections.emptyList(),
                "temperature", 0
        );
    }

    @NotNull
    @Override
    public Map<String, Object> export(@NotNull ChatModelContext context) {
        Map<String, Object> result = ChatModelProvider.super.export(context);
        result.putIfAbsent("model", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable ChatModelContext context) {
        return "Google AI Studio";
    }
}
