package com.example.myapplication;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("post_output.php")
    Call<List<Post>> getPosts();

}
