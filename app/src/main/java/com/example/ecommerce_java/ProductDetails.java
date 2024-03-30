package com.example.ecommerce_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails extends AppCompatActivity {


    private List<Model> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        int productId = getIntent().getIntExtra("productId", -1);
        if (productId != -1) {
            // Make API call to get product details using productId
            getProductDetails(productId);
        } else {
            // Handle error: productId not provided
        }
    }

    private void getProductDetails(int productId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<Model> call = apiService.getProductDetails(productId);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    Model product = response.body();
                    // Handle product details
                    displayProductDetails(product);
                } else {
                    // Handle API call failure
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                // Handle network failure
            }
        });
    }

    private void displayProductDetails(Model product) {
        ImageView mainImage = findViewById(R.id.image);
        TextView titleTextView = findViewById(R.id.title);
        TextView cateTextView = findViewById(R.id.category);
        TextView descTextView = findViewById(R.id.description);
        TextView priceTextView = findViewById(R.id.price);
        TextView ratingTextView = findViewById(R.id.rateing);


        // Load image using Glide
        Glide.with(this)
                .load(product.getImage())
                .into(mainImage);

        titleTextView.setText(product.getTitle());
        cateTextView.setText(product.getCategory());
        descTextView.setText(product.getDescription());
        priceTextView.setText("$" + String.valueOf(product.getPrice()));
        ratingTextView.setText("Rating: " + product.getRating().getRate() + " (" + product.getRating().getCount() + " reviews)");

    }

}



