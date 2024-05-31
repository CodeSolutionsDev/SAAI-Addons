package dev.bluetree242.saaiaddons.aistudio.api.request;

import dev.bluetree242.saaiaddons.aistudio.api.Content;
import dev.langchain4j.model.output.TokenUsage;

import java.util.List;

public record ChatResponse(List<Candidate> candidates, UsageMetadata usageMetadata) {

    public record Candidate(Content content) {
    }

    public record UsageMetadata(int promptTokenCount, int candidatesTokenCount, int totalTokenCount) {

        public TokenUsage toTokenUsage() {
            return new TokenUsage(promptTokenCount, candidatesTokenCount, totalTokenCount);
        }
    }
}
