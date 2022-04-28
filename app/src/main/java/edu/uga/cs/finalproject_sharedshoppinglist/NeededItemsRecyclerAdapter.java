package edu.uga.cs.finalproject_sharedshoppinglist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseError;
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
public class NeededItemsRecyclerAdapter extends RecyclerView.Adapter<NeededItemsRecyclerAdapter.NeededItemHolder> {

    public final String DEBUG_TAG = "NeededRecyclerAdapter";

    private List<NeededItem> NeededItemList;

    public NeededItemsRecyclerAdapter(List<NeededItem> NeededItemList) {
        this.NeededItemList = NeededItemList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class NeededItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageButton removeButton;
        private TextView itemName;
        private TextView quantity;
        private DatabaseReference neededItemsDatabase;
        private String itemNameString;
        private int quantityInt;

        private NeededItem item;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        public NeededItemHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemNameString = itemName.toString();
            quantity = (TextView) itemView.findViewById(R.id.quantityNeededItem);

            removeButton = (ImageButton) itemView.findViewById(R.id.removeButton);

            removeButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
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

        }
    }

        @Override
        public NeededItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.needed_item, parent, false);
            return new NeededItemHolder(view);
        }


        // This method fills in the values of the Views to show a needed item
        @Override
        public void onBindViewHolder(@NonNull final NeededItemHolder holder, int position) {
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

