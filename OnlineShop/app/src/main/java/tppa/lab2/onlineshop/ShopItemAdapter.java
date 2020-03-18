package tppa.lab2.onlineshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.ViewHolder> {

    private ArrayList<ShopItem> shopItems;
    private OnItemClickListener listener;
    public static ArrayList<ShopItem> userCartItems;

    private Context context;


    public ShopItemAdapter(ArrayList<ShopItem> shopItems, Context parentContext) {
        this.shopItems = shopItems;
        userCartItems = new ArrayList<>();
        context = parentContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, this.listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ShopItem currentShopItem = shopItems.get(position);

        holder.productImageView.setImageResource(currentShopItem.getProductImageResource());
        holder.productNameView.setText(currentShopItem.getProductName());
        holder.productPriceView.setText(String.format("%s $", currentShopItem.getProductPrice()));

        holder.productAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCartItems.add(currentShopItem);
                Toast.makeText(context, currentShopItem.getProductName() + " added to cart!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.shopItems.size();
    }

    public static ArrayList<ShopItem> getUserCartItems() {
        return userCartItems;
    }

    public ShopItem getItemOnPosition(int position) {
        return this.shopItems.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView productImageView;
        public TextView productNameView;
        public TextView productPriceView;
        public Button productAddToCartButton;

        public ViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);

            productImageView = itemView.findViewById(R.id.product_image);
            productNameView = itemView.findViewById(R.id.product_name);
            productPriceView = itemView.findViewById(R.id.product_price);
            productAddToCartButton = itemView.findViewById(R.id.add_to_cart);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

}
