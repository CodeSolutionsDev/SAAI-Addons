package dev.bluetree242.saaiaddons.aistudio;

import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.config.option.OptionMap;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelContext;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelProvider;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GeminiHarmBlockThreshold;
import dev.langchain4j.model.googleai.GeminiHarmCategory;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.output.Response;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GoogleAiStudioChatModelProvider implements ChatModelProvider<GoogleAiStudioChatModelProvider.GoogleAiWrapper> {
    private static final Map<GeminiHarmCategory, GeminiHarmBlockThreshold> safetySettings;

    static {
        Map<GeminiHarmCategory, GeminiHarmBlockThreshold> result = new HashMap<>();
        for (GeminiHarmCategory value : GeminiHarmCategory.values()) {
            result.put(value, GeminiHarmBlockThreshold.BLOCK_NONE);
        }
        safetySettings = Collections.unmodifiableMap(result);
    }

    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public GoogleAiWrapper provide(@NotNull ChatModelContext context) {
        OptionMap options = context.options();
        String model = options.getString("model");
        if (model.isBlank()) throw new IllegalStateException("Please set the model for Google AI Studio chat model.");
        return new GoogleAiWrapper(GoogleAiGeminiChatModel.builder()
                .safetySettings(safetySettings)
                .modelName(model)
                .timeout(Duration.ofSeconds(options.getLong("timeout")))
                .maxOutputTokens(options.getIntegerOrDefault("max_output_tokens", i -> i == 0, null))
                .stopSequences(options.getList("stop").getStringList())
                .apiKey(api.getCredentialsRegistry().getConfigured(GoogleAiStudioAddon.NAME, GoogleAiStudioCredentialsLoader.class))
                .build());
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

    public record GoogleAiWrapper(GoogleAiGeminiChatModel model) implements ChatLanguageModel {
        @Override
        public Response<AiMessage> generate(List<ChatMessage> messages) {
            return model.generate(UserMessage.userMessage(messages.stream().filter(m -> m instanceof SystemMessage).map(m -> (SystemMessage) m).map(SystemMessage::text).collect(Collectors.joining("\n\n\n\n"))));
        }
    }
}
