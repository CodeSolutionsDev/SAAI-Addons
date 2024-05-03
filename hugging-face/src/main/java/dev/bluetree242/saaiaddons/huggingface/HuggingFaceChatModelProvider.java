package dev.bluetree242.saaiaddons.huggingface;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
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
    public HuggingFaceChatModel provide(@NotNull Map<String, Object> options) {
        String accessToken = api.getCredentialsRegistry().getConfigured(HuggingFaceAddon.NAME, HuggingFaceCredentialsLoader.class);
        Integer maxNewTokens = (Integer) options.get("max_new_tokens");
        String model = (String) options.get("model");
        double temperature = Double.parseDouble(options.get("temperature").toString());
        if (model.isBlank()) throw new IllegalStateException("Please set the model for hugging face chat model.");
        return new HuggingFaceChatModel.Builder().accessToken(accessToken)
                .modelId(model)
                .timeout(Duration.ofSeconds(Long.parseLong(options.get("timeout").toString())))
                .waitForModel(true)
                .returnFullText(false)
                .maxNewTokens(maxNewTokens == 0 ? null : maxNewTokens)
                .temperature(temperature == 0 ? null : temperature)
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
    public Map<String, Object> export(@NotNull Map<String, Object> options) {
        Map<String, Object> result = ChatModelProvider.super.export(options);
        result.putIfAbsent("model", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable Map<String, Object> options) {
        return "HuggingFace";
    }
}
