package edu.uga.cs.finalproject_sharedshoppinglist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RoommateRecyclerAdapter extends RecyclerView.Adapter<edu.uga.cs.finalproject_sharedshoppinglist.RoommateRecyclerAdapter.RoommateHolder>{

    public final String DEBUG_TAG = "RoommateRecyclerAdapter";

    private List<Roommate> roommateList;

    public RoommateRecyclerAdapter(List<Roommate> roommateList) {
        this.roommateList = roommateList;
    }

    public class RoommateHolder extends RecyclerView.ViewHolder {

        private TextView roommateName;
        private TextView roommateSpent;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        public RoommateHolder(View itemView) {
            super(itemView);

            roommateName = (TextView) itemView.findViewById(R.id.roommateName);
            roommateSpent = (TextView) itemView.findViewById(R.id.roommateSpent);

        }


    }

    @NonNull
    @Override
    public RoommateRecyclerAdapter.RoommateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spent_per_roommate, parent, false);
        return new RoommateRecyclerAdapter.RoommateHolder(view);
    }

    // This method fills in the values of the Views to show a needed item
    @Override
    public void onBindViewHolder(final edu.uga.cs.finalproject_sharedshoppinglist.RoommateRecyclerAdapter.RoommateHolder holder, int position) {
        Roommate roommate = roommateList.get(position);

        Log.d(DEBUG_TAG, "onBindViewHolder: " + roommate);

        holder.roommateName.setText(roommate.getRoommateName());
        holder.roommateSpent.setText(String.valueOf(roommate.getSpent()));

    }

    @Override
    public int getItemCount() {
        return roommateList.size();
    }
}
