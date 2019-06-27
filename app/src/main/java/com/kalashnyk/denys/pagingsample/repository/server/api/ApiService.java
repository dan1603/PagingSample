package com.kalashnyk.denys.pagingsample.repository.server.api;

import com.kalashnyk.denys.pagingsample.repository.database.entities.UserEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/api/android/rest/api-v2.php/records/users2")
    Call<ArrayList<UserEntity>> getUsers(@Query("order") String order,
                                    @Query("page") int page);
}
