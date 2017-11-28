package cc.somkiat.myapplication;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostsService {

    @GET("/posts/{id}")
    Call<Post> getPostBy(@Path("id") int id);

}
