package com.slashmobilty.seleccion.daniel.pinto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView text;
    TextView reverse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.response_1);
        reverse = findViewById(R.id.response_2);
    }

    //Call List Activity

    public void goToList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);

    }

    //Call service method, when pressing the corresponding button

    public void getPosts(View view) {

        //Define the retrofit builder
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://httpbin.org")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<JsonObject> call = apiService.getPost();

        //Check if URL is correct (For debugging)
        String url = apiService.getPost().request().url().toString();

        //Call the service, since it's async we use enqueue
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                //Check if website doesn't return error code like 404
                if(!response.isSuccessful()) {
                    text.setText("Code: " + response.code());
                    return;
                }

                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                //Extract "origin" form the response and set it to the textView
                JsonObject post =  response.body();
                String prev = post.get("origin").toString();
                text.setText(prev);

                //Use StringBuffer and reverse function to complete the request
                StringBuffer c = new StringBuffer(prev);
                reverse.setText(c.reverse());
                }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                //Set text to the textView for debugging
                text.setText(t.getMessage());
            }
        });
    }
}
