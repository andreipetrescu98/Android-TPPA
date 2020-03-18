package tppa.lab2.onlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productNameTextView;
    private TextView productDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productImageView = findViewById(R.id.imageView);
        productNameTextView = findViewById(R.id.productNameTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);

        this.loadProductLayout();
    }

    private void loadProductLayout() {
        Intent intent = getIntent();
        ShopItem currentShopItem = (ShopItem) intent.getSerializableExtra(MainActivity.EXTRA_PRODUCT);

        assert currentShopItem != null;
        productImageView.setImageResource(currentShopItem.getProductImageResource());
        productNameTextView.setText(currentShopItem.getProductName());
        productDescriptionTextView.setText(currentShopItem.getProductDescription());
    }
}
