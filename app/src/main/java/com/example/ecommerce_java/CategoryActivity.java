package com.example.ecommerce_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private ProductAdapter productAdapter;
    private List<Model> productList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        RecyclerView caterc = findViewById(R.id.cateRec);
        caterc.setHasFixedSize(true);
        caterc.setLayoutManager(new GridLayoutManager(this, 2));

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("category")) {
            String category = intent.getStringExtra("category");
            // Call the API to fetch products by category
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<List<Model>> call = apiService.getProductsByCategory(category);
            call.enqueue(new Callback<List<Model>>() {
                @Override
                public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                    if (response.isSuccessful()) {
                        productList = response.body();
                        // Update the RecyclerView with the fetched products
                        productAdapter = new ProductAdapter(productList, CategoryActivity.this, CategoryActivity.this);
                        caterc.setAdapter(productAdapter);
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<List<Model>> call, Throwable t) {
                    // Handle failure
                }
            });
        } else {
            // Handle missing category
        }
    }


    @Override
    public void onProductClick(int productId) {

        Intent intent = new Intent(this, ProductDetails.class);
        intent.putExtra("productId", productId);
//        intent.putExtra("productList", (Serializable) productList);
        startActivity(intent);
    }
}