package edu.uga.cs.finalproject_sharedshoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreenActivity extends AppCompatActivity {
    private static final String TAG = "HomeScreenActivity";

    private Button neededItems;
    private Button purchasedItems;
    private Button totals;
    private Button addNew;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("Shared Shopping List");

        FirebaseUser user = mAuth.getCurrentUser();
        Log.i(TAG, "user = " + user.getEmail());


        logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new LogoutListener());

        neededItems = findViewById(R.id.neededButton);
        neededItems.setOnClickListener(new NeededItemsListener());

        purchasedItems = findViewById(R.id.purchasedItems);
        purchasedItems.setOnClickListener(new PurchasedItemsListener());

        addNew = findViewById(R.id.addItem);
        addNew.setOnClickListener(new AddItemListener());

        totals = findViewById(R.id.totalsButton);
        totals.setOnClickListener(new TotalsListener());



    } // onCreate

    private class LogoutListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.i(TAG, "logout button has been pressed");
            mAuth.signOut();
            if (mAuth.getCurrentUser() == null ) {
                Log.i(TAG, "user has been signed out");
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            } // if

        } // onClick
    } // logoutListener

    private class NeededItemsListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.i(TAG, "view needed items button has been pressed");
                Intent intent = new Intent(view.getContext(), NeededItemsActivity.class);
                startActivity(intent);
        } // onClick

    } // NeededItemsListener

    private class AddItemListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.i(TAG, "add item button has been pressed");
            Intent intent = new Intent(view.getContext(), NewNeededItemActivity.class);
            startActivity(intent);
        } // onClick

    } // TotalsListener

    private class PurchasedItemsListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.i(TAG, "view purchased items button has been pressed");
            Intent intent = new Intent(view.getContext(), PurchasedItemActivity.class);
            startActivity(intent);

        } // onClick

    } // PurchasedItemsListener

    private class TotalsListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.i(TAG, "totals button has been pressed");
            //Intent intent = new Intent(view.getContext(), TotalsActivity.class);
            //startActivity(intent);
        } // onClick

    } // TotalsListener

} // Home Screen Activity