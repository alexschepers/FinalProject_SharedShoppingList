package edu.uga.cs.finalproject_sharedshoppinglist;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all needed items.
 */
public class PurchasedItemsRecyclerAdapter extends RecyclerView.Adapter<edu.uga.cs.finalproject_sharedshoppinglist.PurchasedItemsRecyclerAdapter.PurchasedItemHolder> {

    public final String DEBUG_TAG = "NeededRecyclerAdapter";

    private List<PurchasedItem> PurchasedItemList;

    public PurchasedItemsRecyclerAdapter( List<PurchasedItem> PurchasedItemList ) {
        this.PurchasedItemList = PurchasedItemList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class PurchasedItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageButton removeButton;
        public Button purchaseButton;
        TextView itemName;
        TextView quantity;
        private EditText itemNameView;
        private EditText quantityView;
        private DatabaseReference purchasedItems;

        private NeededItem item;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();



        public PurchasedItemHolder(View itemView ) {
            super(itemView);

            itemName = (TextView) itemView.findViewById( R.id.itemName );
            quantity = (TextView) itemView.findViewById( R.id.quantityNeededItem );
            removeButton = (ImageButton) itemView.findViewById(R.id.removeButton);
            purchaseButton = (Button) itemView.findViewById(R.id.purchase);

            removeButton.setOnClickListener(this);
            purchaseButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.removeButton:
                    String toMatch = PurchasedItemList.get(getAdapterPosition()).getItemName();
                    PurchasedItemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), PurchasedItemList.size());

                    purchasedItems = myRef.child("purchasedItems");
                    Query query = purchasedItems.orderByChild("itemName");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                PurchasedItem purchasedItem = postSnapshot.getValue(PurchasedItem.class);
                                if (purchasedItem.getItemName().equals(toMatch)) {
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

            }
        }
    }

    @Override
    public edu.uga.cs.finalproject_sharedshoppinglist.PurchasedItemsRecyclerAdapter.PurchasedItemHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.needed_item, parent, false );
        return new edu.uga.cs.finalproject_sharedshoppinglist.PurchasedItemsRecyclerAdapter.PurchasedItemHolder( view );
    }


    // This method fills in the values of the Views to show a needed item
    @Override
    public void onBindViewHolder(@NonNull PurchasedItemHolder holder, int position ) {
        PurchasedItem purchasedItem = PurchasedItemList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + purchasedItem );

        holder.itemName.setText( purchasedItem.getItemName());
        holder.quantity.setText( String.valueOf(purchasedItem.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return PurchasedItemList.size();
    }


}



