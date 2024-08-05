package dev.bluetree242.saaiaddons.aistudio.api.response;

public record EmbedResponse(Embedding[] embeddings) {

    public record Embedding(Float[] value) {
    }
}
