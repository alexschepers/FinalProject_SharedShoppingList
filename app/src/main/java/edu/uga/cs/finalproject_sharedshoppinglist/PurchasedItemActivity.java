package edu.uga.cs.finalproject_sharedshoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PurchasedItemActivity extends AppCompatActivity {
    private static final String TAG = "NeededItemsActivity";


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;
    private Button removeButton;

    private List<PurchasedItem> purchasedItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_item);

        setTitle("Shared Shopping List");

        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );

        // use a linear layout manager for the recycler view
        layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager( layoutManager );

        // get a Firebase DB instance reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("purchasedItems");

        purchasedItemList = new ArrayList<PurchasedItem>();


        //removeButton.setOnClickListener(new ButtonClickListener());


        // Set up a listener (event handler) to receive a value for the database reference, but only one time.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method.
        // We can use this listener to retrieve the current list of JobLeads.
        // Other types of Firebase listeners may be set to listen for any and every change in the database
        // i.e., receive notifications about changes in the data in real time (hence the name, Realtime database).
        // This listener will be invoked asynchronously, as no need for an AsyncTask, as in the previous apps
        // to maintain job leads.
        myRef.addListenerForSingleValueEvent( new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, knowing that this is a list,
                // we need to iterate over the elements and place them on a List.
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    PurchasedItem purchasedItem = postSnapshot.getValue(PurchasedItem.class);
                    purchasedItemList.add(purchasedItem);
                }

                // Now, create a JobLeadRecyclerAdapter to populate a RecyclerView to display the needed items.
                recyclerAdapter = new PurchasedItemsRecyclerAdapter( purchasedItemList );
                recyclerView.setAdapter( recyclerAdapter );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        } );


    }
}