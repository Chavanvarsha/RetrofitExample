package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
   private TextView textView;
   private JsonPlaceholderApi jsonPlaceholderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.text_view_result);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceholderApi=retrofit.create(JsonPlaceholderApi.class);
      //  getPosts();
         getComments();

    }
    private void getPosts()
    {
        Call<List<Post>> call=jsonPlaceholderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful())
                {
                    textView.setText("code:"+response.code());
                    return;
                }
                List<Post>posts=response.body();

                for(Post post:posts)
                {
                    String content="";
                    content +="ID: "+post.getId() + "\n";
                    content +="User ID: "+post.getUserId() + "\n";
                    content +="Title: "+post.getTitle() + "\n";
                    content +="Text: "+post.getText() + "\n\n";
                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void getComments()
    {
        Call<List<Comment>> call=jsonPlaceholderApi.getComments();
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful())
                {
                    textView.setText("code:" + response.code());
                    return;
                }
                List<Comment>comments=response.body();

                for(Comment comment:comments)
                {
                    String content="";
                    content +="ID: "+ comment.getId() + "\n";
                    content +="Post ID: "+ comment.getPostId() + "\n";
                    content +="Name: "+ comment.getName() + "\n";
                    content +="Email: "+ comment.getEmail() + "\n";
                    content +="Text: "+ comment.getText() + "\n\n";
                    textView.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());

            }
        });

    }


}
