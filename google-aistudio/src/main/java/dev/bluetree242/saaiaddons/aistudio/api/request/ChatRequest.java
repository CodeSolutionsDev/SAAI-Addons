package dev.bluetree242.saaiaddons.aistudio.api.request;


import dev.bluetree242.saaiaddons.aistudio.api.Content;

import java.util.List;

public record ChatRequest(List<Content> contents, List<SafetySetting> safetySettings,
                          GenerationConfig generationConfig) {

    public record GenerationConfig(List<String> stopSequences, Double temperature, Double topP,
                                   Double topK, Integer maxOutputTokens, Integer candidateCount) {
    }

    public record SafetySetting(HarmCategory category, Threshold threshold) {

        public enum Threshold {
            BLOCK_LOW_AND_ABOVE, BLOCK_MEDIUM_AND_ABOVE, BLOCK_ONLY_HIGH, BLOCK_NONE
        }

        public enum HarmCategory {
            HARM_CATEGORY_HARASSMENT, HARM_CATEGORY_HATE_SPEECH, HARM_CATEGORY_SEXUALLY_EXPLICIT, HARM_CATEGORY_DANGEROUS_CONTENT
        }
    }
}
