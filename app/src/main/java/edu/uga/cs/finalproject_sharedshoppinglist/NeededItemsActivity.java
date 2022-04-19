package edu.uga.cs.finalproject_sharedshoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NeededItemsActivity extends AppCompatActivity {
    private static final String TAG = "NeededItemsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needed_items);

        setTitle("Shared Shopping List");

        // maybe a floating action button here to add items to list


    }
}