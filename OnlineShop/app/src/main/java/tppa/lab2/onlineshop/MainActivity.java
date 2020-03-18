package tppa.lab2.onlineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private ShopItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static final String EXTRA_PRODUCT = "tppa.lab2.onlineshop.EXTRA_PRODUCT";

    private int CLICKS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ShopItem> shopItemsList = this.getShopItems();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ShopItemAdapter(shopItemsList);

        this.loadRecyclerView();

        if (savedInstanceState != null) {
            this.CLICKS = savedInstanceState.getInt("clicks");
        }

        Log.i(TAG, "onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:
                this.openUserSignInDialog();
                return true;
            case R.id.cart:
                this.openUserCart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ShopItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);

                MainActivity.this.incrementClicks();

                intent.putExtra(EXTRA_PRODUCT, (Serializable) adapter.getItemOnPosition(position));
                startActivity(intent);
            }
        });
    }

    private void incrementClicks() {
        this.CLICKS += 1;
    }

    private ArrayList<ShopItem> getShopItems() {
        ArrayList<ShopItem> shopItemsList = new ArrayList<>();

        shopItemsList.add(new ShopItem("Apple IPhone XS", 700.99f, "Apple IPhone XS 64GB Space Gray 4GB RAM", R.drawable.apple_logo));
        shopItemsList.add(new ShopItem("Apple IPhone 11 Pro", 700.99f, "Apple IPhone 11 256GB Midnight Green 4GB RAM", R.drawable.apple_logo));
        shopItemsList.add(new ShopItem("Samsung Galaxy S20", 999.99f, "Samsung Galaxy S20 128GB Blue 8GB RAM", R.drawable.samsung_logo));
        shopItemsList.add(new ShopItem("Samsung Galaxy Note10+", 550.99f, "Samsung Galaxy Note10+ 512GB Black 10GB RAM", R.drawable.samsung_logo));
        shopItemsList.add(new ShopItem("Asus ROG Phone II", 450.99f, "Asus ROG Phone II 64GB Black 12GB RAM", R.drawable.asus_logo));
        shopItemsList.add(new ShopItem("OnePlus 7T", 400.99f, "OnePlus 7T 32GB Green 8GB RAM", R.drawable.oneplus_logo));

        return shopItemsList;
    }

    private void openUserSignInDialog() {
        SignInDialog signInDialog = new SignInDialog();
        signInDialog.show(getSupportFragmentManager(), "Sign In");
    }

    private void openUserCart() {
        Log.i(TAG, "Total cart size: " + String.valueOf(ShopItemAdapter.getUserCartItems().size()));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("clicks", this.CLICKS);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, String.valueOf(CLICKS));
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy");
    }

}
