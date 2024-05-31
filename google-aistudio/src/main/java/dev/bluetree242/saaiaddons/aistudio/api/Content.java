package dev.bluetree242.saaiaddons.aistudio.api;

import dev.langchain4j.data.message.ChatMessage;

import java.util.List;

public record Content(String role, List<Part> parts) {

    @SuppressWarnings("deprecation")
    public static Content fromMessage(ChatMessage message) {
        switch (message.type()) {
            case AI -> {
                return new Content("model", List.of(new Part(message.text())));
            }
            case USER -> {
                return new Content("user", List.of(new Part(message.text())));
            }
            case SYSTEM -> {
                return new Content("system", List.of(new Part(message.text())));
            }
            default -> throw new IllegalArgumentException();
        }
    }

    public record Part(String text) {
    }
}
