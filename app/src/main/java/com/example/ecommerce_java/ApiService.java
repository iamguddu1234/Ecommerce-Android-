package com.example.ecommerce_java;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("products")
    Call<List<Model>> getProducts();


    @GET("products/{id}")
    Call<Model> getProductDetails(@Path("id") int productId);


    @GET("products/categories")
    Call<List<String>> getProductCategories();


    @GET("products/category/{category}")
    Call<List<Model>> getProductsByCategory(@Path("category") String category);

}
