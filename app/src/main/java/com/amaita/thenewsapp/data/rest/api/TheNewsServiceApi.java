package com.amaita.thenewsapp.data.rest.api;

import com.amaita.thenewsapp.data.rest.response.TopHeadline;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Esta interfaz provee la lista de servicios disponibles para obtener la informacion
 * requerida en el APP
 */
public interface TheNewsServiceApi {

    @GET("top-headlines")
    Call<TopHeadline> getTopHeadline(@Query("country") String country);
}
