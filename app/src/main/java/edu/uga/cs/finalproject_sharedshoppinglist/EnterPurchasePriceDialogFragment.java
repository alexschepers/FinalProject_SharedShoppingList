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


public class EnterPurchasePriceDialogFragment extends DialogFragment {

    private EditText itemPrice;

    // This interface will be used to obtain the new job lead from an AlertDialog.
    // A class implementing this interface will handle the new job lead, i.e. store it
    // in Firebase and add it to the RecyclerAdapter.
    public interface EnterPurchasePriceDialogListener {
        //void onFinishPurchasePriceDialog(NeededItem neededItem);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the AlertDialog view
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.fragment_enter_purchase_price_dialog,
                (ViewGroup) getActivity().findViewById(R.id.root));

        // get the view objects in the AlertDialog
        itemPrice = layout.findViewById( R.id.priceDialog);

        // create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set its view (inflated above).
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "Add to Purchase List" );
        // Provide the negative button listener
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });
        // Provide the positive button listener
        builder.setPositiveButton( android.R.string.ok, new EnterPurchasePriceDialogFragment.ButtonClickListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class ButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // this is where we should make a database with username and update their total price

            /*


            // get the Activity's listener to add the new job lead
            AddNeededItemDialogFragment.AddNeededItemDialogListener listener = (AddNeededItemDialogFragment.AddNeededItemDialogListener) getActivity();
            // add the new job lead
            listener.onFinishNewNeededItemDialog( newItem );
            // close the dialog

             */
            dismiss();
        }
    }
}