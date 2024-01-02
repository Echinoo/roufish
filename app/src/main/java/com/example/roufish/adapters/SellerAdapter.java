package com.example.roufish.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.R;
import com.example.roufish.items.ListSeller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.ViewHolder> {

    private List<ListSeller> itemList;
    private Context context;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    public SellerAdapter(Context context, List<ListSeller> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommendation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListSeller item = itemList.get(position);
        holder.textProductName.setText(item.getProductName());
        holder.textPrice.setText(String.valueOf(item.getProductPrice()));;
        String loggedInSellerId = currentUser.getUid();
        if (item.getSellerId().equals(loggedInSellerId)) {
            Picasso.get().load(item.getProductImage()).into(holder.foto);
        } else {
            // Handle the case where the product doesn't belong to the logged-in seller
            // For example, set a default image or hide the ImageView
            holder.foto.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProductName;
        TextView textPrice;
        ImageView foto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textProductName = itemView.findViewById(R.id.productNameTextView);
            textPrice = itemView.findViewById(R.id.productHargaText);
            foto = itemView.findViewById(R.id.productImageView);
            // Initialize other views here
        }
    }
}
