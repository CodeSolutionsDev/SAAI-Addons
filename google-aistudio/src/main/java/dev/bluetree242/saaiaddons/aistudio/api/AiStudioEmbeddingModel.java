package dev.bluetree242.saaiaddons.aistudio.api;

import dev.bluetree242.saaiaddons.aistudio.Utils;
import dev.bluetree242.saaiaddons.aistudio.api.request.EmbedRequest;
import dev.bluetree242.saaiaddons.aistudio.api.response.EmbedResponse;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AiStudioEmbeddingModel implements EmbeddingModel {
    private final AiStudioClient client;
    private final String model;

    public AiStudioEmbeddingModel(String apiKey, String model, Duration timeout) {
        this.client = new AiStudioClient(apiKey, timeout);
        this.model = model;
    }

    @Override
    public Response<List<Embedding>> embedAll(List<TextSegment> textSegments) {
        List<List<TextSegment>> segments = Utils.splitList(textSegments, 100);
        List<Embedding> result = new ArrayList<>();
        for (List<TextSegment> segment : segments) {
            String[] textArray = segment.stream()
                    .map(TextSegment::text)
                    .toArray(String[]::new);

            EmbedRequest request = new EmbedRequest(textArray);
            EmbedResponse response = this.client.embed(request, model);
            result.addAll(convertResponseToEmbeddings(response));
        }

        return Response.from(result);
    }

    public List<Embedding> convertResponseToEmbeddings(EmbedResponse response) {
        if (response.embeddings() == null) {
            throw new IllegalArgumentException("Embeddings data cannot be null");
        }
        List<Embedding> embeddingList = new ArrayList<>();

        // https://github.com/stephanj/langchain4j-cohere/blob/master/src/main/java/dev/langchain4j/model/cohere/CohereEmbeddingModel.java#L81
        // Converts Float[] to float[]
        for (EmbedResponse.Embedding embedding : response.embeddings()) {
            float[] primitiveArray = new float[embedding.value().length];
            for (int i = 0; i < embedding.value().length; i++) {
                Float floatValue = embedding.value()[i];
                primitiveArray[i] = floatValue == null ? 0.0f : floatValue;
            }
            embeddingList.add(new Embedding(primitiveArray));
        }
        return embeddingList;
    }
}
