package dev.bluetree242.saaiaddons.aistudio.api;

import dev.bluetree242.saaiaddons.aistudio.api.request.ChatRequest;
import dev.bluetree242.saaiaddons.aistudio.api.request.ChatResponse;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AiStudioChatLanguageModel implements ChatLanguageModel {
    private final AiStudioClient client;
    private final String model;
    private final Double temperature;
    private final Double topK;
    private final Double topP;
    private final Integer maxOutputTokens;
    private final List<String> stopSequences;

    public AiStudioChatLanguageModel(Builder builder) {
        this.client = new AiStudioClient(builder.apiKey, builder.timeout);
        this.model = builder.model;
        ;
        this.temperature = builder.temperature;
        this.topK = builder.topK;
        this.topP = builder.topP;
        this.maxOutputTokens = builder.maxOutputTokens;
        this.stopSequences = builder.stopSequences;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Response<AiMessage> generate(List<ChatMessage> messages) {
        ChatRequest request = new ChatRequest(
                Collections.singletonList(new Content("user", List.of(new Content.Part(messages.stream().map(ChatMessage::text).collect(Collectors.joining("\n\n\n\n\n")))))),
                Arrays.stream(ChatRequest.SafetySetting.HarmCategory.values()).map(h -> new ChatRequest.SafetySetting(h, ChatRequest.SafetySetting.Threshold.BLOCK_NONE)).toList(),
                new ChatRequest.GenerationConfig(
                        stopSequences,
                        temperature,
                        topP,
                        topK,
                        maxOutputTokens,
                        1
                )
        );
        ChatResponse response = client.chat(request, model);
        return Response.from(AiMessage.from(response.candidates().get(0).content().parts().get(0).text()),
                response.usageMetadata().toTokenUsage());
    }

    public static final class Builder {
        private String apiKey;
        private String model;
        private Duration timeout = Duration.ofSeconds(15);
        private Double temperature;
        private Double topK;
        private Double topP;
        private Integer maxOutputTokens;
        private List<String> stopSequences;

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder temperature(Double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder topK(Double topK) {
            this.topK = topK;
            return this;
        }

        public Builder topP(Double topP) {
            this.topP = topP;
            return this;
        }

        public Builder maxOutputTokens(Integer maxOutputTokens) {
            this.maxOutputTokens = maxOutputTokens;
            return this;
        }

        public AiStudioChatLanguageModel build() {
            return new AiStudioChatLanguageModel(this);
        }

        public Builder stopSequences(List<String> stopSequences) {
            this.stopSequences = stopSequences;
            return this;
        }
    }
}
