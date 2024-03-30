package com.example.ecommerce_java;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    // Define interface for click listener
    public interface OnProductClickListener {
        void onProductClick(int productId);
    }


    private List<Model> productList;
    private Context context;

    private OnProductClickListener listener;


    public ProductAdapter(List<Model> productList, Context context, OnProductClickListener listener) {
        this.productList = productList;
        this.context = context;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
//        Context context = holder.Img.getContext();


        Model model = productList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onProductClick(model.getId());
                }
            }
        });

        holder.Title.setText(model.getTitle());
        holder.price.setText("$" + String.valueOf(model.getPrice()));
        // Concatenate with dollar sign

        // Check if rating is null

        // Check if rating is null
        if (model.getRating() != null) {
            // If not null, set the rating information
            holder.rating.setText("Rating: " + model.getRating().getRate() + " (" + model.getRating().getCount() + " reviews)");
        } else {
            // If null, display a message indicating that the rating is not available
            holder.rating.setText("Rating: N/A");
        }

        if (context != null) {
            Glide.with(context)
                    .load(model.getImage())
                    .into(holder.Img);
        }


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView Img;
        TextView Title;
        TextView price;

        TextView rating;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            Img = itemView.findViewById(R.id.img);
            Title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            rating = itemView.findViewById(R.id.rati);
        }
    }
}
