package tppa.lab2.onlineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private ShopItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static final String EXTRA_PRODUCT = "tppa.lab2.onlineshop.EXTRA_PRODUCT";

    private int CLICKS;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ShopItem> shopItemsList = this.getShopItems();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ShopItemAdapter(shopItemsList, this);

        this.loadRecyclerView();

        // Load credentials from file
        username = loadCredentials();
        if (username != null)
            Toast.makeText(this, "Hello " + username, Toast.LENGTH_LONG).show();

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
            case R.id.sensors:
                this.openSensorsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSensorsActivity() {
        Intent intent = new Intent(MainActivity.this, SensorsActivity.class);
        startActivity(intent);
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

    private String loadCredentials() {
        FileInputStream fis = null;

        try {
            fis = openFileInput(SignInDialog.FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
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
        Intent openCartActivity = new Intent(MainActivity.this, SharedPreferencesActivity.class);
        openCartActivity.putExtra("CART_INFO", ShopItemAdapter.getUserCartItems());
        startActivity(openCartActivity);
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
