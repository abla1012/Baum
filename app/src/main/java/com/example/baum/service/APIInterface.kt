package com.example.baum.service

import com.acme.rentmyride.entity.FahrzeugDTO
import com.example.test.entity.Fahrzeug
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// https://square.github.io/retrofit/

interface APIInterface {

    @GET("api/fahrzeuge")
    fun getFahrzeuge(): Call<List<Fahrzeug>>

    /*
        public interface GitHubService {
        @GET("users/{user}/repos")
        Call<List<Repo>> listRepos(@Path("user") String user);}
     */
    @GET("api/fahrzeuge/{id}")
    fun getFahrzeug(@Path("id") id:String): Call<Fahrzeug>

    /*
        @POST("users/new")
        Call<User> createUser(@Body User user);
     */
    @POST("api/fahrzeuge")
    fun addFahrzeuge(@Body fahrzeug: FahrzeugDTO) : Call<String>

    @DELETE("api/fahrzeuge/{id}")
    fun deleteFahrzeug(@Path("id") id:String): Call<String>
}