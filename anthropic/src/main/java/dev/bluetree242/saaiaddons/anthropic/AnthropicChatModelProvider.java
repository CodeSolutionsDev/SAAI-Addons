package dev.bluetree242.saaiaddons.anthropic;


import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.config.option.OptionMap;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelContext;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelProvider;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AnthropicChatModelProvider implements ChatModelProvider<AnthropicChatModelProvider.AnthropicWrapper> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public AnthropicWrapper provide(@NotNull ChatModelContext context) {
        OptionMap options = context.options();
        String model = options.getString("model");
        if (model.isBlank()) throw new IllegalStateException("Please set the model for Anthropic chat model.");
        return new AnthropicWrapper(
                AnthropicChatModel.builder()
                        .modelName(model)
                        .baseUrl(options.getString("base_url"))
                        .timeout(Duration.ofSeconds(options.getLong("timeout")))
                        .maxTokens(options.getIntegerOrDefault("max_tokens", i -> i == 0, null))
                        .maxRetries(options.getInteger("max_retries"))
                        .stopSequences(options.getList("stop").getStringList())
                        .apiKey(api.getCredentialsRegistry().getConfigured(AnthropicAddon.NAME, AnthropicCredentialsLoader.class))
                        .build()
        );
    }

    @NotNull
    @Override
    public Map<String, Object> getDefaultOptions() {
        return Map.of(
                "base_url", "https://api.anthropic.com/v1/",
                "model", "",
                "timeout", 15L,
                "max_tokens", 0,
                "max_retries", 3,
                "stop", Collections.emptyList()
        );
    }

    @NotNull
    @Override
    public OptionMap export(@NotNull ChatModelContext context) {
        OptionMap result = ChatModelProvider.super.export(context);
        // Makes sure the "model" is always in the config even if it is not configured.
        result.putIfAbsent("model", "");
        return result;
    }

    @Nullable
    @Override
    public String getDisplayName(@Nullable ChatModelContext context) {
        return "Anthropic";
    }

    public record AnthropicWrapper(AnthropicChatModel model) implements ChatModel {
        @Override
        public ChatResponse chat(ChatRequest request) {
            return model.chat(new ChatRequest.Builder()
                    .modelName(request.modelName())
                    .maxOutputTokens(request.maxOutputTokens())
                    .temperature(request.temperature())
                    .stopSequences(request.stopSequences())
                    .parameters(request.parameters())
                    .topK(request.topK())
                    .toolChoice(request.toolChoice())
                    .toolSpecifications(request.toolSpecifications())
                    .responseFormat(request.responseFormat())
                    .messages(UserMessage.userMessage(request.messages().stream().filter(m -> m instanceof SystemMessage).map(m -> (SystemMessage) m).map(SystemMessage::text).collect(Collectors.joining("\n\n\n\n"))))
                    .build());
        }
    }
}