package eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import eg.com.blogspot.httpamrabuelhamd.findmate.CustomItemClickListener;
import eg.com.blogspot.httpamrabuelhamd.findmate.HandlingMainScreenViews.MainScreenDataAdapter;
import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy.singleAprtmentDataPublisher.SingleAprtmentData;
import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy.singleAprtmentDataPublisher.SingleAprtmentDataAdapter;
import eg.com.blogspot.httpamrabuelhamd.findmate.R;
import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder;

/**
 * Created by amro mohamed on 3/2/2018.
 */
// sopped at the button click i should start the real getting data from server to check if the loading
//indicator works or not, also the empty textView.
//TODO make editTextBox to show user his current place then if he wanna change it he just type in it
public class NeedApartmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        LoaderManager.LoaderCallbacks<List<SingleAprtmentData>> {

    private static final String EMADO_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query";//todo change

    /**
     * create a unique id for the instance of my app for this particular app
     * "Because the identifier is globally unique, it can be used to identify a specific app instance."
     * check this http://bit.ly/2qvhmrE
     */
//    SharedPreferences sPrefs= PreferenceManager.getDefaultSharedPreferences(this);
//    final String UNIQE_GUID=sPrefs.getString("key_guid",null);
//todo there error above
    //region create variables used inside the whole class
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
    View loadingIndicator;

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int APARTMENT_LOADER_ID = 1;
    TextView mEmptyStateTextView;
    //endregion

    //region initialise taking user input portion UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_apartment);

        getViews();
        setSpinner();
        setSeekBar();
        initializeRecyclerViewAdapter();

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
    //[END]

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
    String validateAddress() {
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
        egyptGov = UtilsNeedApartment.getGovernorates();
        //find the spinner obj
        spinner = findViewById(R.id.spinner);
        //the second spinner
        spinner2 = findViewById(R.id.spinner2);
        spinner2.setVisibility(View.GONE);

        mEmptyStateTextView = findViewById(R.id.emptyTextView);
        loadingIndicator = findViewById(R.id.progressBar);
    }


    //endregion

    //NOW THE FLOW IS LIKE THIS:
    //WHEN USER CLICK ON THE BUTTON VIRTUALLY I SEND IT AND RECIEVE DATA HERE -INSIDE THE BUTTON CLICK-
    //THEN I START TO INITIALIZE THE RECYCLER ADAPTER -I MAY NEED TO START IT AS A NEW THREAD TO SAVE TIME-
    //THEN THE BUFFER STARTS TILL ALL DATA IS WITH ME THEN I ADD THEM TO THE MODEL CLASS AND NOTIFY THE
    //ADAPTER TO PUT NEW DATA. I GUESS BUFFER WILL STILL TILL MAKING MY NOTIFY
    public void buttonClick_GetResults(View view) {
        //first makes sure user chosen an address
        String s = validateAddress();
        if (!s.equals(""))
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        else {
            //now device is getting data from internet
            loadingIndicator.setVisibility(View.VISIBLE);
            //then
            //TODO send data to the server and get result back
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
                loaderManager.initLoader(APARTMENT_LOADER_ID, null, this);
            } else {
                // Otherwise, display error
                // First, hide loading indicator so error message will be visible
                loadingIndicator.setVisibility(View.GONE);

                // Update empty state with no connection error message
                mEmptyStateTextView.setText(R.string.no_internet_connection);
            }
            //endregion

            prepareMainOptions();
            //makes screen scroll down to show the results to the user
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.smoothScrollTo(0, button.getBottom());
                }
            });

//            initializeRecyclerViewAdapter();

        }
    }

    /************************************/
    private void initializeRecyclerViewAdapter() {
        mRecyclerView = findViewById(R.id.need_apartment_activity_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter and creating on click listener for each obj
        mAdapter = new SingleAprtmentDataAdapter(dataArrayList, new CustomItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent();
                new DragDismissIntentBuilder(context)
                        .setTheme(DragDismissIntentBuilder.Theme.LIGHT)//R.style.mytheme	// LIGHT (default), DARK, BLACK, DAY_NIGHT
                        .setPrimaryColorResource(R.color.colorAccent)    // defaults to a semi-transparent black
                        .setToolbarTitle("Apartment Details")        // defaults to null
                        .setShowToolbar(true)                // defaults to true
                        .setShouldScrollToolbar(true)       // defaults to true
                        .setFullscreenOnTablets(true)      // defaults to false, tablets will have padding on each side
                        .setDragElasticity(DragDismissIntentBuilder.DragElasticity.LARGE)  // Larger elasticities will make it easier to dismiss.
                        .setDrawUnderStatusBar(false)       // defaults to false. Change to true if you don't want me to handle the content margin for the Activity. Does not apply to the RecyclerView Activities
                        .build(intent);

                intent.setClass(context, ApartmentDetails.class);
                intent.putExtra("ApartmentData", dataArrayList.get(position));
//                Log.v("Appart","someData= "+dataArrayList.get(position).getResultId());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
//        prepareMainOptions();

    }

    /***************TO Delete*********************/
    private void prepareMainOptions() {
        dataArrayList.add(new SingleAprtmentData(55555, R.drawable.wnaa2, R.string.mWannaRent,
                R.string.mWannaRent, R.string.mWannaRent, R.string.mWannaRent));
        dataArrayList.add(new SingleAprtmentData(7555, R.drawable.wnaarent2, R.string.mWannaRent,
                R.string.mWannaRent, R.string.mWannaRent, R.string.mWannaRent));

        mAdapter.notifyDataSetChanged();
    }

    //region the background thread
    @Override
    public Loader<List<SingleAprtmentData>> onCreateLoader(int id, Bundle args) {
        //FIRST get address
//            spinnerResult;
//            spinner2Result;
        //SECOND get max and min range
//        updateMinMaxPrice();
//            max;
//            min;
        //THIRD get apartment state
        //possible results are 0,1,2 --> مفروشة ، مش مفروشة ، اي حاجة respectively
//        String i = findViewById(radioGroup.getCheckedRadioButtonId()).getTag().toString();
        //FOURTH get unique id
//        UNIQE_GUID;
//          //todo: now uncomment these bellow
//        //building the url //todo change the bellow parameter to form the agreed url
//        Uri baseUri = Uri.parse(EMADO_REQUEST_URL);
//        Uri.Builder uriBuilder = baseUri.buildUpon();
//
//        uriBuilder.appendQueryParameter("format", "geojson");
//        uriBuilder.appendQueryParameter("limit", "10");
//        uriBuilder.appendQueryParameter("minmag", String.valueOf(min));
//        uriBuilder.appendQueryParameter("orderby", String.valueOf(min));


//        return new ApartmentLoader(this, uriBuilder.toString());
        return new ApartmentLoader(this, "http://192.168.1.13/emad.php");
    }

    @Override
    public void onLoadFinished(Loader<List<SingleAprtmentData>> loader, List<SingleAprtmentData> data) {
//        // Hide loading indicator because the data has been loaded
//        loadingIndicator.setVisibility(View.GONE);
//
//        // Set empty state text to display "No earthquakes found."
//        mEmptyStateTextView.setText(R.string.no_apartments);
//
//        // Clear the adapter of previous apartment data
//        //mAdapter.clear();
//
//        // If there is a valid list of {@link SingleApartmentData}s, then add them to the adapter's
//        // data set. This will trigger the ListView to update.
//        if (data != null && !data.isEmpty()) {
//            dataArrayList.addAll(data);
//            mAdapter.notifyDataSetChanged();
//            mEmptyStateTextView.setVisibility(View.GONE);
//            //updateUi(earthquakes);
//        }
    }

    @Override
    public void onLoaderReset(Loader<List<SingleAprtmentData>> loader) {
        // Loader reset, so we can clear out our existing data.
        dataArrayList.clear();
        mAdapter.notifyDataSetChanged();
//        mRecyclerView.removeAllViewsInLayout(); //try using this one later
        // check this http://bit.ly/2HPnlQB
    }
    //endregion
}