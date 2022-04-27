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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                    PurchasedItemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), PurchasedItemList.size());
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

