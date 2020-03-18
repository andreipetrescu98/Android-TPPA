package tppa.lab2.onlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class SharedPreferencesActivity extends AppCompatActivity {

    private CheckBox saveCartCheckBox;
    private TextView cartInfoTextView;

    public static final String SAVE_CART_INFO = "SAVE_CART_INFO";
    public static final String SHARED_PREFS = "SHARED_PREFS";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefereces);

        ArrayList<ShopItem> cartInfo = null;
        Intent intent = getIntent();
        if (intent != null) {
            cartInfo = (ArrayList<ShopItem>) intent.getSerializableExtra("CART_INFO");
        }

        saveCartCheckBox = findViewById(R.id.save_cart_checkbox);
        cartInfoTextView = findViewById(R.id.cart_text_view);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        saveCartCheckBox.setChecked(sharedPreferences.getBoolean(SAVE_CART_INFO, false));

        int visibility;
        if (saveCartCheckBox.isChecked()) {
            visibility = View.VISIBLE;
        } else {
            visibility = View.GONE;
        }
        setCartInfoTextView(cartInfo);

        cartInfoTextView.setVisibility(visibility);
    }

    void setCartInfoTextView(ArrayList<ShopItem> cart) {
        if (cart != null) {
            StringBuilder sb = new StringBuilder();
            Float cartCost = 0.0f;

            for (ShopItem shopItem:
                    cart) {
                sb.append(shopItem.getProductName());
                sb.append("\n");
                cartCost += shopItem.getProductPrice();
            }

            cartInfoTextView.setText("Cart cost: " + cartCost + "$\n" + sb.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SAVE_CART_INFO, saveCartCheckBox.isChecked());

        editor.apply();
    }
}
