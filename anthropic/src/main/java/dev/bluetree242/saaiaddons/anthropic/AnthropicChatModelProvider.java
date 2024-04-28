package dev.bluetree242.saaiaddons.anthropic;


import dev.bluetree242.serverassistantai.api.ServerAssistantAIAPI;
import dev.bluetree242.serverassistantai.api.registry.chatmodel.ChatModelProvider;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AnthropicChatModelProvider implements ChatModelProvider<AnthropicChatModelProvider.AnthropicWrapper> {
    private final ServerAssistantAIAPI api;

    @NotNull
    @Override
    public AnthropicWrapper provide(@NotNull Map<String, Object> options) {
        String model = (String) options.get("model");
        Integer maxTokens = (Integer) options.get("max_tokens");
        Integer maxRetries = (Integer) options.get("max_retries");
        //noinspection unchecked
        List<String> stop = (List<String>) options.get("stop");
        if (model.isBlank()) throw new IllegalStateException("Please set the model for anthropic chat model.");
        return new AnthropicWrapper(
                AnthropicChatModel.builder()
                        .modelName(model)
                        .baseUrl((String) options.get("base_url"))
                        .timeout(Duration.ofSeconds(Long.parseLong(options.get("timeout").toString())))
                        .maxTokens(maxTokens == 0 ? null : maxTokens)
                        .maxRetries(maxRetries)
                        .stopSequences(stop)
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
    public Map<String, Object> export(@NotNull Map<String, Object> options) {
        Map<String, Object> result = ChatModelProvider.super.export(options);
        // Makes sure the "model" is always in the config even if it is not configured.
        result.putIfAbsent("model", "");
        return result;
    }

    public record AnthropicWrapper(AnthropicChatModel model) implements ChatLanguageModel {
        @Override
        public Response<AiMessage> generate(List<ChatMessage> messages) {
            return model.generate(UserMessage.userMessage(messages.stream().filter(m -> m instanceof SystemMessage).map(m -> (SystemMessage) m).map(SystemMessage::text).collect(Collectors.joining("\n\n\n\n"))));
        }
    }
}
