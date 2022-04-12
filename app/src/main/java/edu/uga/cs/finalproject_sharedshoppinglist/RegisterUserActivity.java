package edu.uga.cs.finalproject_sharedshoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegisterUserActivity extends AppCompatActivity {

    private static final String TAG = "RegisterUserActivity";

    private EditText registerEmail;
    private EditText registerPassword;

    private String registerEmailString;
    private String registerPasswordString;

    private Button registerUser;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        registerUser = findViewById(R.id.confirmRegister);
        registerUser.setOnClickListener(new createUser());

    }

    private class createUser implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Log.i(TAG, "LogIn button has been pressed");

            registerEmail = findViewById(R.id.editTextRegisterEmailAddress);
            registerPassword = findViewById(R.id.editTextRegisterPassword);

            registerEmailString = registerEmail.getText().toString();
            registerPasswordString = registerPassword.getText().toString();

            Log.i(TAG, registerEmailString);
            Log.i(TAG, registerPasswordString);

            if (registerEmailString.equals("") || registerPasswordString.equals("")) {
                Log.i(TAG, "email or password is null");
                Toast.makeText(RegisterUserActivity.this, "Please enter a valid email address & password.",
                        Toast.LENGTH_SHORT).show();

            } else {
                Log.i(TAG, "going into else statement");

                mAuth.createUserWithEmailAndPassword(registerEmailString, registerPasswordString)
                        .addOnCompleteListener(RegisterUserActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterUserActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } // if-else

        } // onClick()

    } // createUser

} // RegisterUserActivity