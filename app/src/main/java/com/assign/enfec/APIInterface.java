package com.assign.enfec;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("users")
    Single<List<Users>> getUsers();

    @GET("posts")
    Single<List<Posts>> getPosts();
}
