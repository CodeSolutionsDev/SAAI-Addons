package dev.bluetree242.saaiaddons.aistudio.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.bluetree242.saaiaddons.aistudio.api.request.ChatRequest;
import dev.bluetree242.saaiaddons.aistudio.api.request.EmbedRequest;
import dev.bluetree242.saaiaddons.aistudio.api.response.ChatResponse;
import dev.bluetree242.saaiaddons.aistudio.api.response.EmbedResponse;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

import static com.google.gson.FieldNamingPolicy.IDENTITY;

public class AiStudioClient {
    private final AiStudioApi api;

    public AiStudioClient(String apiKey, Duration timeout) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ApiKeyInsertingInterceptor(apiKey))
                .callTimeout(timeout)
                .connectTimeout(timeout)
                .readTimeout(timeout)
                .writeTimeout(timeout)
                .build();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(IDENTITY)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://generativelanguage.googleapis.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.api = retrofit.create(AiStudioApi.class);
    }

    private static RuntimeException toException(retrofit2.Response<?> response) throws IOException {

        int code = response.code();
        String body = Objects.requireNonNull(response.errorBody()).string();

        String errorMessage = String.format("status code: %s; body: %s", code, body);
        return new RuntimeException(errorMessage);
    }

    public ChatResponse chat(ChatRequest request, String model) {
        try {
            retrofit2.Response<ChatResponse> retrofitResponse = api.chat(request, model).execute();
            if (retrofitResponse.isSuccessful()) {
                return retrofitResponse.body();
            } else {
                throw toException(retrofitResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public EmbedResponse embed(EmbedRequest request, String model) {
        try {
            retrofit2.Response<EmbedResponse> retrofitResponse = api.embed(request, model).execute();
            if (retrofitResponse.isSuccessful()) {
                return retrofitResponse.body();
            } else {
                throw toException(retrofitResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
