package edu.uga.cs.finalproject_sharedshoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

public class NewNeededItemActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "NewNeededItemActivity";

    private EditText itemNameView;
    private EditText quantityView;
    private Button addItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_needed_item);
        setTitle("Shared Shopping List");

        itemNameView = (EditText) findViewById(R.id.editTextItemName);
        quantityView = (EditText) findViewById(R.id.editTextItemQuantity);

        addItemButton = (Button) findViewById(R.id.addItemButton);

        addItemButton.setOnClickListener(new ButtonClickListener());

    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String itemName = itemNameView.getText().toString();
            String value = quantityView.getText().toString();
            int quantity = Integer.parseInt(value);

            final NeededItem item = new NeededItem(itemName, quantity);

            // Add a new element (JobLead) to the list of job leads in Firebase.

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            //DatabaseReference myRef = database.getReference("jobleads");

            // First, a call to push() appends a new node to the existing list (one is created
            // if this is done for the first time).  Then, we set the value in the newly created
            // list node to store the new job lead.
            // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
            // the previous apps to maintain job leads.

            /*
            myRef.push().setValue( jobLead )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Show a quick confirmation
                            Toast.makeText(getApplicationContext(), "Job lead created for " + jobLead.getCompanyName(),
                                    Toast.LENGTH_SHORT).show();

                            // Clear the EditTexts for next use.
                            companyNameView.setText("");
                            phoneView.setText("");
                            urlView.setText("");
                            commentsView.setText("");
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText( getApplicationContext(), "Failed to create a Job lead for " + jobLead.getCompanyName(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }

             */
        }

    }
}