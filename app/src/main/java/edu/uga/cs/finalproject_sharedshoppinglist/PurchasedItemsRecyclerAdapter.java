package edu.uga.cs.finalproject_sharedshoppinglist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all needed items.
 */
public class PurchasedItemsRecyclerAdapter extends RecyclerView.Adapter<edu.uga.cs.finalproject_sharedshoppinglist.PurchasedItemsRecyclerAdapter.PurchasedItemHolder> {

    public final String DEBUG_TAG = "Purchased...Adapter";

    private List<PurchasedItem> PurchasedItemList;

    public PurchasedItemsRecyclerAdapter( List<PurchasedItem> PurchasedItemList ) {
        this.PurchasedItemList = PurchasedItemList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class PurchasedItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageButton removeButton;
        private ImageButton updateButton;
        private ImageButton doneEditing;
        private EditText itemName;
        private EditText quantity;
        private EditText price;

        private DatabaseReference purchasedItems;
        private DatabaseReference neededItems;
        private DatabaseReference roommates;

        Roommate roommate2;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();



        public PurchasedItemHolder(View itemView ) {
            super(itemView);

            itemName = (EditText) itemView.findViewById( R.id.purchasedName );
            quantity = (EditText) itemView.findViewById( R.id.quantityPurchased );
            removeButton = (ImageButton) itemView.findViewById(R.id.removeButton);
            updateButton = (ImageButton) itemView.findViewById(R.id.updateButton);
            doneEditing = (ImageButton) itemView.findViewById(R.id.doneEditing);
            price = (EditText) itemView.findViewById(R.id.price);

            itemName.setEnabled(false);
            quantity.setEnabled(false);
            price.setEnabled(false);

            removeButton.setOnClickListener(this);
            updateButton.setOnClickListener(this);
            doneEditing.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                // updating button is pressed making everything edittable
                case R.id.updateButton:

                    itemName.setEnabled(true);
                    quantity.setEnabled(true);
                    price.setEnabled(true);
                    doneEditing.setVisibility(View.VISIBLE);
                    break;

                    // done updating button is pressed
                case R.id.doneEditing:

                    itemName.setEnabled(false);
                    quantity.setEnabled(false);
                    price.setEnabled(false);
                    doneEditing.setVisibility(View.INVISIBLE);
                    String name = itemName.getText().toString();

                    int quantityPurchased = Integer.parseInt(quantity.getText().toString());
                    String toSplitPrice = (price.getText().toString()).substring(1);
                    double pricePaid = Double.parseDouble(toSplitPrice);

                    purchasedItems = myRef.child("purchasedItems");

                    Query query = purchasedItems.orderByChild("itemName");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                PurchasedItem purchasedItem = postSnapshot.getValue(PurchasedItem.class);
                                if (purchasedItem.getItemName().equals(name)) {
                                    PurchasedItem toPush = new PurchasedItem(name, quantityPurchased,pricePaid);
                                    postSnapshot.getRef().removeValue();
                                    purchasedItems.push().setValue(toPush);
                                    Log.i(DEBUG_TAG, "found a match, in if statement");
                                }
                                Log.i(DEBUG_TAG, dataSnapshot.getRef().toString());
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError firebaseError) {
                        }

                    });


                    break;

                case R.id.removeButton:
                    String toMatch = PurchasedItemList.get(getAdapterPosition()).getItemName();
                    PurchasedItemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), PurchasedItemList.size());

                    purchasedItems = myRef.child("purchasedItems");
                    neededItems = myRef.child("neededItems");

                    Query query1 = purchasedItems.orderByChild("itemName");
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                PurchasedItem purchasedItem = postSnapshot.getValue(PurchasedItem.class);
                                if (purchasedItem.getItemName().equals(toMatch)) {
                                    NeededItem toPush = new NeededItem(purchasedItem.getItemName(), purchasedItem.getQuantity());
                                    neededItems.push().setValue(toPush);
                                    postSnapshot.getRef().removeValue();
                                    Log.i(DEBUG_TAG, "found a match, in if statement");
                                }
                                Log.i(DEBUG_TAG, dataSnapshot.getRef().toString());
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError firebaseError) {
                        }

                    });

                    String priceToSplit = (price.getText().toString()).substring(1);
                    double pricePaidToSubtract = Double.parseDouble(priceToSplit);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String userEmail = user.getEmail();
                    roommates = myRef.child("roommates");

                    Query roommateQuery = roommates.orderByChild("spent");
                    roommateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Roommate roommate = postSnapshot.getValue(Roommate.class);
                                if (roommate.getRoommateName().equals(userEmail)) {
                                    Double newPrice = roommate.getSpent() - pricePaidToSubtract;
                                    postSnapshot.getRef().removeValue();
                                    roommate2 = new Roommate(userEmail, newPrice);
                                }
                            }
                            Log.i(DEBUG_TAG, String.valueOf(roommate2));
                            roommates.push().setValue(roommate2);

                        }

                        @Override
                        public void onCancelled(DatabaseError firebaseError) {
                        }


                    });
                    break;


                default:
                    throw new IllegalStateException("Unexpected value: " + view.getId());
            }
        }
    }

    private void editPurchasedDialog() {

    }

    @Override
    public edu.uga.cs.finalproject_sharedshoppinglist.PurchasedItemsRecyclerAdapter.PurchasedItemHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.purchased_item, parent, false );
        return new edu.uga.cs.finalproject_sharedshoppinglist.PurchasedItemsRecyclerAdapter.PurchasedItemHolder( view );
    }


    // This method fills in the values of the Views to show a needed item
    @Override
    public void onBindViewHolder(@NonNull PurchasedItemHolder holder, int position ) {
        PurchasedItem purchasedItem = PurchasedItemList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + purchasedItem );

        holder.itemName.setText( purchasedItem.getItemName());
        holder.quantity.setText( String.valueOf(purchasedItem.getQuantity()));
        holder.price.setText("$" + String.valueOf(purchasedItem.getPrice()));

    }

    @Override
    public int getItemCount() {
        return PurchasedItemList.size();
    }


}



