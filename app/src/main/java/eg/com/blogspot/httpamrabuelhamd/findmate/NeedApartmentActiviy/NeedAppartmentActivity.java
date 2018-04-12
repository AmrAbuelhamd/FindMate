package eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;

import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy.singleAprtmentDataPublisher.SingleAprtmentData;
import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy.singleAprtmentDataPublisher.SingleAprtmentDataAdapter;
import eg.com.blogspot.httpamrabuelhamd.findmate.R;

/**
 * Created by amro mohamed on 3/2/2018.
 */
//TODO make editTextBox to show user his current place then if he wanna change it he just type in it
public class NeedAppartmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final Context context = this;
    ArrayList<String> egyptGov;
    ArrayList<String> subRegoin;
    Spinner spinner;
    Spinner spinner2;
    ScrollView scrollView;
    Button button;
    String spinnerResult = "";
    String spinner2Result = "";
    int min;
    int max;
    RangeSeekBar<Integer> rangeSeekBar;
    RadioGroup radioGroup;
    //some var for recycler
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<SingleAprtmentData> dataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_apartment);

        getViews();
        setSpinner();
        setSeekBar();
    }

    private void setSeekBar() {
        //play around with rang bar
        rangeSeekBar = findViewById(R.id.rang_seek_bar);

        rangeSeekBar.setRangeValues(100, 5000);
        rangeSeekBar.setSelectedMinValue(120);
        rangeSeekBar.setSelectedMaxValue(2000);
        rangeSeekBar.setTextAboveThumbsColorResource(android.R.color.black);
        rangeSeekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editText.requestFocus();//not working with the seekbar, don't know why
                //TODO i mieght need this later
            }
        });
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {

            }
        });
    }

    private void setSpinner() {
        //set listener when user selects a governorate
        spinner.setOnItemSelectedListener(this);
        //prepare spinner
        initislizeSpinner(egyptGov, R.string.firstSpinnerHint, spinner);
    }

    //set spinner adapter and etc etc
    private void initislizeSpinner(ArrayList<String> arrayList, int firstElementStringId,
                                   Spinner spinner) {
        arrayList.add(0, getResources().getString(firstElementStringId));
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayList) {
            //[START making_spinnerHint]
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
            //[END making_spinnerHint]
        };

        // Specify the layout to use when the list of choices appears
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(dataAdapter);

    }

    //[START handling user spinner selection]
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //TODO modify this to handle all choices
        //this only handle the first two elements

        if (position == 1 || position == 2) {
            subRegoin = NeedApartmentUtils.getSubregions(position);
            spinner2.setVisibility(View.VISIBLE);
            initislizeSpinner(subRegoin, R.string.secondSpinnerHint, spinner2);
            //set second spinner onclick stuff
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //TODO if there sibling to what he choosen then create another spinner here
                    spinner2Result = parent.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }

            });
        } else {
            spinner2.setVisibility(View.GONE);
        }
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        spinnerResult = parent.getSelectedItem().toString();

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    //[END]

    //NOW THE FLOW IS LIKE THIS:
    //WHEN USER CLICK ON THE BUTTON VIRTUALLY I SEND IT AND RECIEVE DATA HERE -INSIDE THE BUTTON CLICK-
    //THEN I START TO INITIALIZE THE RECYCLER ADAPTER -I MAY NEED TO START IT AS A NEW THREAD TO SAVE TIME-
    //THEN THE BUFFER STARTS TILL ALL DATA IS WITH ME THEN I ADD THEM TO THE MODEL CLASS AND NOTIFY THE
    //ADAPTER TO PUT NEW DATA. I GUESS BUFFER WILL STILL TILL MAKING MY NOTIFY
    public void buttonClick_GetResults(View view) {
        //first makes sure user chosen an address
        String s = validateAdress();
        if (!s.equals(""))
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        else {
            //FIRST get address
//            spinnerResult;
//            spinner2Result;
            //SECOND get max and min range
            updateMinMaxPrice();
//            max;
//            min;
            //THIRD get apartment state
            //possible results are 0,1,2 --> مفروشة ، مش مفروشة ، اي حاجة respectively
            String i = findViewById(radioGroup.getCheckedRadioButtonId()).getTag().toString();
            //FOURTH get device id  //there is some risk here read https://goo.gl/5ZCyzB
            String android_id = Secure.getString(context.getContentResolver(),
                    Secure.ANDROID_ID);
            //then
            //TODO send data to the server and get result back

            //makes screen scroll down to show the results to the user
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.smoothScrollTo(0, button.getBottom());
                }
            });

            initializeRecyclerViewAdapter();

        }
    }

    private void initializeRecyclerViewAdapter() {
        mRecyclerView = findViewById(R.id.need_apartment_activity_recycler_view);
Log.v("ini","mrecy = "+mRecyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SingleAprtmentDataAdapter(dataArrayList);
        mRecyclerView.setAdapter(mAdapter);
        prepareMainOptions();
        //TODO fix:  each time i click the button it append more data and puts mpore views
    }
    /***************TO Delete*********************/
    private void prepareMainOptions() {
        dataArrayList.add(new SingleAprtmentData(55555,R.drawable.wnaa2,R.string.mWannaRent,
                R.string.mWannaRent,R.string.mWannaRent,R.string.mWannaRent));
        dataArrayList.add(new SingleAprtmentData(55555,R.drawable.wnaarent2,R.string.mWannaRent,
                R.string.mWannaRent,R.string.mWannaRent,R.string.mWannaRent));

        mAdapter.notifyDataSetChanged();
    }
    /************************************/

    /**
     * getting price bar results
     */
    private void updateMinMaxPrice() {//TODO maybe make calculation to make sure his choose makes sense
        min = rangeSeekBar.getSelectedMinValue();
        max = rangeSeekBar.getSelectedMaxValue();
    }

    /**
     * validate address
     */
    String validateAdress() {
        String errorMsg = "";
        if (spinnerResult.equals(getResources().getString(R.string.firstSpinnerHint)))
            errorMsg += getResources().getString(R.string.onNothingSelected);
        if (spinner2Result.equals(getResources().getString(R.string.secondSpinnerHint)))
            errorMsg += "\n" + getResources().getString(R.string.onNothingSelected2);

        return errorMsg;
    }

    public void getViews() {
        //get radio group object
        radioGroup = findViewById(R.id.radioGroup);
        //get the root view which is scroll view
        scrollView = findViewById(R.id.scrollView);
        //get the button
        button = findViewById(R.id.button);
        //get egypt governorate
        egyptGov = NeedApartmentUtils.getGovernorates();
        //find the spinner obj
        spinner = findViewById(R.id.spinner);
        //the second spinner
        spinner2 = findViewById(R.id.spinner2);
        spinner2.setVisibility(View.GONE);
    }
}
