package dev.bluetree242.saaiaddons.aistudio.api;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static dev.langchain4j.internal.ValidationUtils.ensureNotBlank;

public class ApiKeyInsertingInterceptor implements Interceptor {

    private final String apiKey;

    ApiKeyInsertingInterceptor(String apiKey) {
        this.apiKey = ensureNotBlank(apiKey, "apiKey");
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter("key", apiKey).build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
