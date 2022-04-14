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

} // Home Screen Activity