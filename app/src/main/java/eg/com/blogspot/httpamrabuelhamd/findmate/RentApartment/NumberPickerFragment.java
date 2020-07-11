package eg.com.blogspot.httpamrabuelhamd.findmate.RentApartment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import eg.com.blogspot.httpamrabuelhamd.findmate.R;

/**
 * Created by amro mohamed on 5/3/2018.
 */

public class NumberPickerFragment extends DialogFragment implements NumberPicker.OnValueChangeListener{
    Button view;




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //get sent arguments from the calling activity

        int min = getArguments().getInt("min");
        int max = getArguments().getInt("max");

        

        final NumberPicker numberPicker = new NumberPicker(getActivity());

        numberPicker.setMinValue(min);
        numberPicker.setMaxValue(max);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.roomNum);//todo fix it's alignment later
        builder.setMessage(R.string.choose);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onValueChange(numberPicker,
                        numberPicker.getValue(), numberPicker.getValue());
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setView(numberPicker);
        return builder.create();

    }


    public void setCallingView(View b){
        view  = (Button) b;
    }
    /**
     * GET CALLED WHEN USER CHOOSE NUMBER FROM THE NUMBER PICKER DIALOG
     * @param picker THE CHOSEN NUMBER
     * @param oldVal
     * @param newVal
     */
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        view.setText(String.valueOf(picker.getValue()));
    }
}