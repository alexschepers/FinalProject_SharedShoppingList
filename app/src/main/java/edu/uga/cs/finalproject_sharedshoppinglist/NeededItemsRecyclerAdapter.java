package edu.uga.cs.finalproject_sharedshoppinglist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all needed items.
 */
public class NeededItemsRecyclerAdapter extends RecyclerView.Adapter<edu.uga.cs.finalproject_sharedshoppinglist.NeededItemsRecyclerAdapter.NeededItemHolder> {

    public final String DEBUG_TAG = "NeededRecyclerAdapter";

    private List<NeededItem> NeededItemList;

    public NeededItemsRecyclerAdapter( List<NeededItem> NeededItemList ) {
        this.NeededItemList = NeededItemList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class NeededItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageButton removeButton;
        public Button purchaseButton;
        TextView itemName;
        TextView quantity;
        private EditText itemNameView;
        private EditText quantityView;

        private DatabaseReference purchasedItems;

        public NeededItemHolder(View itemView ) {
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
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();

                    purchasedItems = myRef.child("purchasedItems");

                    purchasedItems.push().setValue(NeededItemList.get(getAdapterPosition()));
                    //remove item from shopping list when it has been marked as purchased and moved to the purchased list
                    NeededItemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), NeededItemList.size());
                    break;
            }
        }
    }

    @Override
    public edu.uga.cs.finalproject_sharedshoppinglist.NeededItemsRecyclerAdapter.NeededItemHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.needed_item, parent, false );
        return new edu.uga.cs.finalproject_sharedshoppinglist.NeededItemsRecyclerAdapter.NeededItemHolder( view );
    }


    // This method fills in the values of the Views to show a needed item
    @Override
    public void onBindViewHolder(final edu.uga.cs.finalproject_sharedshoppinglist.NeededItemsRecyclerAdapter.NeededItemHolder holder, int position ) {
        NeededItem neededItem = NeededItemList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + neededItem );

        holder.itemName.setText( neededItem.getItemName());
        holder.quantity.setText( String.valueOf(neededItem.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return NeededItemList.size();
    }

    /**
     * This is an adapter class for the RecyclerView to show all needed items.
     */
    public static class PurchasedItemsRecyclerAdapter extends RecyclerView.Adapter<PurchasedItemsRecyclerAdapter.PurchasedItemHolder> {

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
                PurchasedItemList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), PurchasedItemList.size());

            }
        }

        @Override
        public PurchasedItemHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
            View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.needed_item, parent, false );
            return new PurchasedItemHolder( view );
        }


        // This method fills in the values of the Views to show a needed item
        @Override
        public void onBindViewHolder(final PurchasedItemHolder holder, int position ) {
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
}

