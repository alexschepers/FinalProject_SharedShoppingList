package edu.uga.cs.finalproject_sharedshoppinglist;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddNeededItemDialogFragment extends DialogFragment {

    private EditText itemName;
    private EditText itemQuantity;

    // This interface will be used to obtain the new job lead from an AlertDialog.
    // A class implementing this interface will handle the new job lead, i.e. store it
    // in Firebase and add it to the RecyclerAdapter.
    public interface AddNeededItemDialogListener {
        void onFinishNewNeededItemDialog(NeededItem neededItem);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the AlertDialog view
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.fragment_add_needed_item_dialog,
                (ViewGroup) getActivity().findViewById(R.id.root));

        // get the view objects in the AlertDialog
        itemName = layout.findViewById( R.id.dialogFragmentName );
        itemQuantity = layout.findViewById( R.id.dialogFragmentQuantity);

        // create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set its view (inflated above).
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "New Needed Item" );
        // Provide the negative button listener
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });
        // Provide the positive button listener
        builder.setPositiveButton( android.R.string.ok, new ButtonClickListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class ButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String itemNameString = itemName.getText().toString();
            String value = itemQuantity.getText().toString();
            int quantity = Integer.parseInt(value);

            NeededItem newItem = new NeededItem( itemNameString, quantity);

            // get the Activity's listener to add the new job lead
            AddNeededItemDialogListener listener = (AddNeededItemDialogListener) getActivity();
            // add the new job lead
            listener.onFinishNewNeededItemDialog( newItem );
            // close the dialog
            dismiss();
        }
    }
}