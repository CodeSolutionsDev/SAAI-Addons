package dev.bluetree242.saaiaddons.aistudio;

import dev.bluetree242.saaiaddons.aistudio.api.AiStudioChatLanguageModel;
import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelProvider;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class GoogleAIStudioChatModelProvider implements ChatModelProvider<AiStudioChatLanguageModel> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public AiStudioChatLanguageModel provide(@NotNull Map<String, Object> options) {
        String model = (String) options.get("model");
        Integer maxTokens = (Integer) options.get("max_output_tokens");
        //noinspection unchecked
        List<String> stop = (List<String>) options.get("stop");
        if (model.isBlank()) throw new IllegalStateException("Please set the model for Google AI Studio chat model.");
        return AiStudioChatLanguageModel.builder()
                .model(model)
                .timeout(Duration.ofSeconds(Long.parseLong(options.get("timeout").toString())))
                .maxOutputTokens(maxTokens == 0 ? null : maxTokens)
                .stopSequences(stop)
                .apiKey(api.getCredentialsRegistry().getConfigured(GoogleAIStudioAddon.NAME, GoogleAIStudioCredentialsLoader.class))
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
    public Map<String, Object> export(@NotNull Map<String, Object> options) {
        Map<String, Object> result = ChatModelProvider.super.export(options);
        result.putIfAbsent("model", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable Map<String, Object> options) {
        return "Google AI Studio";
    }
}
