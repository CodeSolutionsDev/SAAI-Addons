package dev.bluetree242.saaiaddons.aistudio.api;

import dev.bluetree242.saaiaddons.aistudio.api.request.ChatRequest;
import dev.bluetree242.saaiaddons.aistudio.api.request.ChatResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AiStudioApi {

    @POST("/v1beta/models/{model}:generateContent")
    @Headers({"accept: application/json", "content-type: application/json"})
    Call<ChatResponse>
    chat(@Body ChatRequest request, @Path("model") String model);

}
