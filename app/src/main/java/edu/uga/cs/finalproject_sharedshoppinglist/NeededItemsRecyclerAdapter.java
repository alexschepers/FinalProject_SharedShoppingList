package edu.uga.cs.finalproject_sharedshoppinglist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * This is an adapter class for the RecyclerView to show all job leads.
 */
public class NeededItemsRecyclerAdapter extends RecyclerView.Adapter<NeededItemsRecyclerAdapter.NeededItemHolder> {

    public final String DEBUG_TAG = "NeededRecyclerAdapter";

    private List<NeededItem> NeededItemList;

    public NeededItemsRecyclerAdapter( List<NeededItem> NeededItemList ) {
        this.NeededItemList = NeededItemList;
    }

    // The adapter must have a ViewHolder class to "hold" one item to show.
    class NeededItemHolder extends RecyclerView.ViewHolder {

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

    // This method fills in the values of the Views to show a JobLead
    @Override
    public void onBindViewHolder( NeededItemHolder holder, int position ) {
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

