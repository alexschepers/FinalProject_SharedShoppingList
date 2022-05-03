package edu.uga.cs.finalproject_sharedshoppinglist;

import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all needed items.
 */
public class NeededItemsRecyclerAdapter extends RecyclerView.Adapter<edu.uga.cs.finalproject_sharedshoppinglist.NeededItemsRecyclerAdapter.NeededItemHolder> {

    public final String DEBUG_TAG = "NeededRecyclerAdapter";

    private List<NeededItem> NeededItemList;

    public NeededItemsRecyclerAdapter(List<NeededItem> NeededItemList) {
        this.NeededItemList = NeededItemList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class NeededItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageButton removeButton;
        public ImageButton updateButton;
        public ImageButton doneEditing;
        public Button purchaseButton;
        TextView itemName;
        TextView quantity;
        private EditText itemNameView;
        private EditText quantityView;
        private EditText priceInput;
        private DatabaseReference neededItemsDatabase;
        private String itemNameString;
        private int quantityInt;

        private DatabaseReference purchasedItems;
        private DatabaseReference neededItems;

        private DatabaseReference roommates;

        private NeededItem item;

        Roommate addToRoommate;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        public NeededItemHolder(View itemView) {
            super(itemView);
            priceInput = (EditText) itemView.findViewById(R.id.editTextPrice);
            itemName = (TextView) itemView.findViewById(R.id.itemName);
            quantity = (TextView) itemView.findViewById(R.id.quantityNeededItem);
            removeButton = (ImageButton) itemView.findViewById(R.id.removeButton);
            updateButton = (ImageButton) itemView.findViewById(R.id.updateButton);
            doneEditing= (ImageButton) itemView.findViewById(R.id.doneEditing);
            purchaseButton = (Button) itemView.findViewById(R.id.purchaseButton);

            removeButton.setOnClickListener(this);
            purchaseButton.setOnClickListener(this);
            updateButton.setOnClickListener(this);
            doneEditing.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            //Editable price = priceInput.getText();

            switch (view.getId()) {
                case R.id.removeButton:
                    String toMatch = NeededItemList.get(getAdapterPosition()).getItemName();
                    NeededItemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), NeededItemList.size());

                    neededItemsDatabase = myRef.child("neededItems");
                    Query query = neededItemsDatabase.orderByChild("itemName");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                NeededItem neededItem = postSnapshot.getValue(NeededItem.class);
                                if (neededItem.getItemName().equals(toMatch)) {
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
                    break;
                case R.id.purchaseButton:
                    if (priceInput.getText().toString() != null){
                        Double priceDouble = Double.parseDouble(priceInput.getText().toString());
                        DecimalFormat decim = new DecimalFormat("0.00");
                        Double price2 = Double.parseDouble( decim.format(priceDouble));

                        String moveToPurchase = NeededItemList.get(getAdapterPosition()).getItemName();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();

                        // adding price to roommate part of database
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userEmail = user.getEmail();
                        roommates = myRef.child("roommates");
                        //addToRoommate = new Roommate (userEmail, price2);
                        //roommates.push().setValue(addToRoommate);

                        Query roommateQuery = roommates.orderByChild("spent");
                        roommateQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Roommate roommate = postSnapshot.getValue(Roommate.class);
                                    if (roommate.getRoommateName().equals(userEmail)) {
                                        Double newPrice = price2 + roommate.getSpent();
                                        postSnapshot.getRef().removeValue();
                                        addToRoommate = new Roommate(userEmail, newPrice);
                                    } else {
                                        addToRoommate = new Roommate (userEmail, price2);
                                    }
                                }
                                Log.i(DEBUG_TAG, String.valueOf(addToRoommate));
                                roommates.push().setValue(addToRoommate);

                            }

                            @Override
                            public void onCancelled(DatabaseError firebaseError) {
                            }


                        });




                        // adding to purchased items list
                        purchasedItems = myRef.child("purchasedItems");
                        NeededItem toChange = NeededItemList.get(getAdapterPosition());
                        PurchasedItem toAdd = new PurchasedItem (toChange.getItemName(), toChange.getQuantity(), price2 );

                        purchasedItems.push().setValue(toAdd);


                        //remove item from shopping list when it has been marked as purchased and moved to the purchased list
                        NeededItemList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), NeededItemList.size());

                        neededItemsDatabase = myRef.child("neededItems");
                        Query purchaseQuery = neededItemsDatabase.orderByChild("itemName");
                        purchaseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    NeededItem neededItem = postSnapshot.getValue(NeededItem.class);
                                    if (neededItem.getItemName().equals(moveToPurchase)) {
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
                    }else{
                        Toast.makeText(view.getContext(), "Please enter a valid price", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.updateButton:
                    itemName.setEnabled(true);
                    quantity.setEnabled(true);
                    doneEditing.setVisibility(View.VISIBLE);
                    break;

                case R.id.doneEditing:
                    itemName.setEnabled(false);
                    quantity.setEnabled(false);
                    doneEditing.setVisibility(View.INVISIBLE);
                    String name = itemName.getText().toString();

                    int quantityPurchased = Integer.parseInt(quantity.getText().toString());
                   //String toSplitPrice = (priceInput.getText().toString()).substring(1);
                    //double pricePaid = Double.parseDouble(toSplitPrice);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    neededItems = myRef.child("neededItems");

                    Query query1 = neededItems.orderByChild("itemName");
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                PurchasedItem purchasedItem = postSnapshot.getValue(PurchasedItem.class);
                                if (purchasedItem.getItemName().equals(name)) {
                                    NeededItem toPush = new NeededItem(name, quantityPurchased);
                                    postSnapshot.getRef().removeValue();
                                    neededItems.push().setValue(toPush);
                                    Log.i(DEBUG_TAG, "found a match, in if statement");
                                }
                                Log.i(DEBUG_TAG, dataSnapshot.getRef().toString());
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError firebaseError) {
                        }

                    });
            }
        }
    }



    @NonNull
    @Override
    public NeededItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.needed_item, parent, false);
        return new NeededItemHolder(view);
    }



/*
    @Override
    public edu.uga.cs.finalproject_sharedshoppinglist.NeededItemsRecyclerAdapter.NeededItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.needed_item, parent, false);
        return new edu.uga.cs.finalproject_sharedshoppinglist.NeededItemsRecyclerAdapter.NeededItemHolder(view);
    }*/


    // This method fills in the values of the Views to show a needed item
    @Override
    public void onBindViewHolder(final edu.uga.cs.finalproject_sharedshoppinglist.NeededItemsRecyclerAdapter.NeededItemHolder holder, int position) {
        NeededItem neededItem = NeededItemList.get(position);

        Log.d(DEBUG_TAG, "onBindViewHolder: " + neededItem);

        holder.itemName.setText(neededItem.getItemName());
        holder.quantity.setText(String.valueOf(neededItem.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return NeededItemList.size();
    }
}