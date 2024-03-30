package com.example.ecommerce_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.util.List;
import java.util.PrimitiveIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener, CateAdapter.OnCategoryClickListener {

    private ProductAdapter productAdapter;


    private CateAdapter cateAdapter;
    private List<Model> productList;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView prv = findViewById(R.id.productRec);
        prv.setHasFixedSize(true);
        prv.setLayoutManager(new GridLayoutManager(this, 2));


        RecyclerView cateRV = findViewById(R.id.cateRec);
        cateRV.setLayoutManager(new GridLayoutManager(this, 2));


        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<List<Model>> call = apiService.getProducts();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                if (response.isSuccessful()) {
                    List<Model> products = response.body();

                    productAdapter = new ProductAdapter(products, MainActivity.this, MainActivity.this);
                    prv.setAdapter(productAdapter);


                } else {
                    // Handle errors
                }
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {

            }
        });

        Call<List<String>> call2 = apiService.getProductCategories();
        call2.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> categories = response.body();
                if (categories != null) {
                    // Categories retrieved successfully, pass them to the adapter
                    cateAdapter = new CateAdapter(categories,MainActivity.this);
                    cateRV.setAdapter(cateAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });

    }


    @Override
    public void onProductClick(int productId) {

        Intent intent = new Intent(this, ProductDetails.class);
        intent.putExtra("productId", productId);
        intent.putExtra("productList", (Serializable) productList);
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);

    }
}