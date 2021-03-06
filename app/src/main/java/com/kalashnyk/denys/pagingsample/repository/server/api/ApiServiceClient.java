package com.kalashnyk.denys.pagingsample.repository.server.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kalashnyk.denys.pagingsample.utils.Constants.API_BASE_URL;
import static com.kalashnyk.denys.pagingsample.utils.Constants.USER_ARRAY_LIST_CLASS_TYPE;

public class ApiServiceClient {

    public static ApiService getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(USER_ARRAY_LIST_CLASS_TYPE, new JsonDeserializerHelper())
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(API_BASE_URL);

        return builder.build().create(ApiService.class);
    }
}
