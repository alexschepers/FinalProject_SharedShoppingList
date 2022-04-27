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

import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all needed items.
 */
public class NeededItemsRecyclerAdapter extends RecyclerView.Adapter<NeededItemsRecyclerAdapter.NeededItemHolder> {

    public final String DEBUG_TAG = "NeededRecyclerAdapter";

    private List<NeededItem> NeededItemList;

    public NeededItemsRecyclerAdapter( List<NeededItem> NeededItemList ) {
        this.NeededItemList = NeededItemList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class NeededItemHolder extends RecyclerView.ViewHolder {

        public Button removeButton;
        TextView itemName;
        TextView quantity;

        public NeededItemHolder(View itemView ) {
            super(itemView);

            itemName = (TextView) itemView.findViewById( R.id.itemName );
            quantity = (TextView) itemView.findViewById( R.id.quantityNeededItem );
        }
    }

    @Override
    public NeededItemHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.needed_item, parent, false );
        return new NeededItemHolder( view );
    }


    // This method fills in the values of the Views to show a needed item
    @Override
    public void onBindViewHolder(@NonNull final NeededItemHolder holder, int position ) {
        NeededItem neededItem = NeededItemList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + neededItem );

        holder.itemName.setText( neededItem.getItemName());
        holder.quantity.setText( String.valueOf(neededItem.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return NeededItemList.size();
    }
}

