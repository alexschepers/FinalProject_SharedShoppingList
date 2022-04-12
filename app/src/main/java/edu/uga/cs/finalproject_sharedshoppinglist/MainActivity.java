package edu.uga.cs.finalproject_sharedshoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button logInButton;
    private Button registerButton;

    private EditText emailEditText;
    private EditText passwordEditText;

    private String email;
    private String password;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInButton = findViewById(R.id.confirmRegister);
        logInButton.setBackgroundColor(Color.BLUE);
        logInButton.setOnClickListener(new LogInListener());


        registerButton = findViewById(R.id.register);
        registerButton.setBackgroundColor(Color.LTGRAY);
        registerButton.setOnClickListener(new registerListener());

    }

    private class LogInListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.i(TAG, "LogIn button has been pressed");

            emailEditText = findViewById(R.id.editTextEmailAddress);
            passwordEditText = findViewById(R.id.editTextPassword);

            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();

            Log.i(TAG, email);
            Log.i(TAG, password);

            if (email.equals("") || password.equals("")) {
                Log.i(TAG, "email or password is null");
                Toast.makeText(MainActivity.this, "Please enter a valid email address & password.",
                        Toast.LENGTH_SHORT).show();

            } else {
                Log.i(TAG, "going into else statement");
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.i(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }


        }
    }



    private class registerListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.i(TAG, "Register button has been pressed");
            Intent intent = new Intent( view.getContext(), RegisterUserActivity.class );
            startActivity( intent );

        }
    }

}