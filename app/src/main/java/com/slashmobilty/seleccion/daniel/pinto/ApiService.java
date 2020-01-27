package com.slashmobilty.seleccion.daniel.pinto;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    //Created an interface to define the Api Route and the HTTP method to use
    String API_ROUTE = "get";

    //Since the API returns a single Object, we create the call as a JsonObject
    @GET(API_ROUTE)
    Call<JsonObject> getPost();
}
