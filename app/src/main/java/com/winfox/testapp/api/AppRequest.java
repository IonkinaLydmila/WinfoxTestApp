package com.winfox.testapp.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AppRequest {

    private RequestModel requestModel;
    private APIDataCallback callback;

    public AppRequest(RequestModel requestModel, APIDataCallback callback) {

        this.requestModel = requestModel;
        this.callback = callback;

    }

    public void init() {

        OkHttpClient client = getOkHttpClient().newBuilder().build();
        Call call = client.newCall(createRequest());
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                error(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                success(response);
            }

        });

    }

    private OkHttpClient getOkHttpClient() {

        return new OkHttpClient()
                .newBuilder()
                .build();

    }

    private Request createRequest() {

        Request.Builder builder = new Request.Builder();

        switch (requestModel.type) {
            case POST: { builder.post(requestModel.body); }
            case GET: { builder.get(); }
            case PUT: { builder.put(requestModel.body); }
            case DELETE: { builder.delete(); }
        }

        builder.url(requestModel.url);
        return builder.build();

    }

    private void error(IOException e) {

        String error = "No internet connection";
        if(e instanceof SocketTimeoutException) error = "Timeout error";
        callback.serverResponse(false, error);

    }

    private void success(Response response) {

        String result = null;

        try {

            if(response.body() != null) result = response.body().string();

            if(result == null) {
                callback.serverResponse(false, "Response is null");
                return;
            }

            if(response.code() == 200) callback.serverResponse(true, result);
            else {

                ErrorResponseModel responseModel = ErrorResponseModel.mapping(new JSONObject(result));
                callback.serverResponse(false, responseModel.getError());

            }

        } catch (JSONException | IOException e) {

            if(result != null) callback.serverResponse(false, result);
            else callback.serverResponse(false, response.toString());

        }

    }

}
