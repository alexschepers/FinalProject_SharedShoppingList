package edu.uga.cs.finalproject_sharedshoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PurchasedItemActivity extends AppCompatActivity {

    public static final String TAG = "PurchasedItemActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_item);

        setTitle("Shared Shopping List");


    }
}