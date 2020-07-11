package eg.com.blogspot.httpamrabuelhamd.findmate.RentApartment;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartment.UtilsNeedApartment;
import eg.com.blogspot.httpamrabuelhamd.findmate.R;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by amro mohamed on 4/27/2018.
 */

public class RentApartmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        LoaderManager.LoaderCallbacks<String>{

    private static final int RENT_LOADER_ID = 5;
    ArrayList<String> egyptGov;
    ArrayList<String> subRegoin;
    Spinner spinner;
    Spinner spinner2;
    String spinnerResult = "";
    String spinner2Result = "";

    CustomTextInputLayout adName,rentPrice;
    TextInputEditText adNameTEXT,rentPriceTEXT;
    Spinner periodSpinner;
    RadioGroup radioGroup;
    int periodSpinnerwResult;
    int isFurnshed;
    ProgressBar indicator;
    Button roomNum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_rent_apartment);

        getViews();
        setSpinner();
        preparePeriodSpinner();

        //region fixing inputtextlayout problem with error
        adNameTEXT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adName.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rentPriceTEXT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            rentPrice.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //endregion

        //use this to handle some vies together see
        // https://stackoverflow.com/questions/42118674/how-to-group-multiple-views-in-a-constraintlayout
//        Group group=(Group)findViewById(R.id.group);//bind view from xml
//        group.setVisibility(View.VISIBLE);//this will visible all views
//        group.setVisibility(View.GONE);//this will set Gone to all views

    }

    private void preparePeriodSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.period_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        periodSpinner.setAdapter(adapter);
        periodSpinner.setSelection(4);
        periodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                periodSpinnerwResult = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void getViews(){
        //get egypt governorate
        egyptGov = UtilsNeedApartment.getGovernorates();
        //get radio group object
        radioGroup = findViewById(R.id.radioGroup);
        //find the spinner obj
        spinner = findViewById(R.id.citySpinner);
        //the second spinner
        spinner2 = findViewById(R.id.subCitySpinner);
        spinner2.setVisibility(View.GONE);

        radioGroup = findViewById(R.id.radioGroup2);
        rentPrice = findViewById(R.id.rentPriceLayout);
        rentPriceTEXT = findViewById(R.id.rentPrice);

        adName = findViewById(R.id.titleEditText);
        adNameTEXT = findViewById(R.id.titleText);

        periodSpinner = findViewById(R.id.periodSpinner);
        indicator  = findViewById(R.id.rentApartInidcator);

        roomNum = findViewById(R.id.button4);
    }

    private void setSpinner() {
        //set listener when user selects a governorate
        spinner.setOnItemSelectedListener(this);
        //prepare spinner
        initializeSpinner(egyptGov, R.string.firstSpinnerHint, spinner);
    }
    //set spinner adapter and etc etc
    private void initializeSpinner(ArrayList<String> arrayList, int firstElementStringId,
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
            subRegoin = UtilsNeedApartment.getSubregions(position);
            spinner2.setVisibility(View.VISIBLE);
            initializeSpinner(subRegoin, R.string.secondSpinnerHint, spinner2);
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

    /**
     * triggered when user clicks the button
     * @param view
     */
    public void saveDataToServer(View view) {
        String s = validateEditText();
        if (!s.equals(""))
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), s,
                    Snackbar.LENGTH_LONG)
                    .show();
        else {
            indicator.setVisibility(View.VISIBLE);
            isFurnshed = getRadioResult();

//            Toast.makeText(this, spinnerResult + " " +
//                    spinner2Result + " " + isFurnshed + " " + periodSpinnerwResult + " " + rentPrice.getText().toString()
//                    + " ", Toast.LENGTH_LONG).show();
            //region here i start the background thread
            // Get a reference to the ConnectivityManager to check state of network connectivity
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // If there is a network connection, fetch data
            if (networkInfo != null && networkInfo.isConnected()) {
                // Get a reference to the LoaderManager, in order to interact with loaders.
                LoaderManager loaderManager = getLoaderManager();

                // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                // because this activity implements the LoaderCallbacks interface).
                loaderManager.initLoader(RENT_LOADER_ID, null, this);
            } else {
                // Otherwise, display error
                // First, hide loading indicator so error message will be visible
                indicator.setVisibility(View.GONE);
//
//                // Update empty state with no connection error message
                Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.no_internet_connection,
                        Snackbar.LENGTH_LONG)
                        .show();
            }
            //endregion
        }
    }
    String validateEditText() {
        String errorMsg = "";
        if (spinnerResult.equals(getResources().getString(R.string.firstSpinnerHint))){
            errorMsg += getResources().getString(R.string.onNothingSelected)+"\n";
            TextView errorText = (TextView)spinner.getSelectedView();
            errorText.setTextColor(Color.RED);//just to highlight that this is an error

        }
        if (spinner2Result.equals(getResources().getString(R.string.secondSpinnerHint))) {
            errorMsg += getResources().getString(R.string.onNothingSelected2) + "\n";
            TextView errorText = (TextView)spinner2.getSelectedView();
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
        }
        if(TextUtils.isEmpty(adNameTEXT.getText())) {
            adName.setError("لازم تكتب اسم للأعلان");
            errorMsg += "** " + adNameTEXT.getHint() + "\n";
        }
        if(TextUtils.isEmpty(rentPriceTEXT.getText())) {
            rentPrice.setError("لازم تكتب سعر للايجار");
            errorMsg += "** " + rentPrice.getHint() + "\n";
        }



        return errorMsg;
    }
    int getRadioResult(){
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.furnishedRadio:
                return 1;
            case R.id.notFurnishedRadio:
                return 0;
            default:
                return 0;
        }
    }

    //String EMADO_REQUEST_URL = "http://192.168.1.5/exam/amr.php";
    String EMADO_REQUEST_URL = "http://192.168.1.13/emad.php";
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        RequestBody formBody = new FormBody.Builder()
                .add("action", "createApartment")
                .add("title", adNameTEXT.getText().toString())
                .add("price", rentPriceTEXT.getText().toString())
                .add("price_per", String.valueOf(periodSpinnerwResult))
                .add("furnished", String.valueOf(isFurnshed))
                .add("location_id", String.valueOf(1))
                .build();
        return new RentApartLoader(this, EMADO_REQUEST_URL,formBody);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        indicator.setVisibility(View.GONE);

        if(data.length()!=0) {
            startActivity(new Intent(this, StatActivity.class));
        }
        else {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.somethingWentWrong,
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        loader = null;
    }


    public void shownumberpicker(View view) {

        Bundle bundle = new Bundle();
        bundle.putInt("min", 1);
        bundle.putInt("max", 8);

        callTheFragment(view,bundle);

    }

    public void shownumberpickerForFloor(View view) {

        Bundle bundle = new Bundle();
        bundle.putInt("min", 0);
        bundle.putInt("max", 25);

        callTheFragment(view,bundle);
    }

    void callTheFragment(View view,Bundle bundle){
        NumberPickerFragment newFragment = new NumberPickerFragment();
        newFragment.setCallingView(view);
        newFragment.show(getSupportFragmentManager(), "time picker");
        newFragment.setArguments(bundle);
    }
}
