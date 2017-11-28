package cc.somkiat.myapplication;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView detail = findViewById(R.id.detail);

        OkHttpClient client = new OkHttpClient();
        IdlingResource resource = OkHttp3IdlingResource.create("OkHttp", client);
        IdlingRegistry.getInstance().register(resource);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build();

        PostsService service = retrofit.create(PostsService.class);
        service.getPostBy(1).enqueue(new Callback<Post>() {

            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();
                detail.setText(post.getTitle());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                detail.setText(t.getMessage());
            }
        });
    }
}
