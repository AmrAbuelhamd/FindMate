package eg.com.blogspot.httpamrabuelhamd.findmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
    import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.UUID;

import eg.com.blogspot.httpamrabuelhamd.findmate.HandlingMainScreenViews.MainScreenData;
import eg.com.blogspot.httpamrabuelhamd.findmate.HandlingMainScreenViews.MainScreenDataAdapter;
import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy.NeedApartmentActivity;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<MainScreenData> dataArrayList = new ArrayList<>();
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //specify an adapter and creating on click listener for each obj
        mAdapter = new MainScreenDataAdapter(dataArrayList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(position == 0) {
                    Intent intent = new Intent(context, NeedApartmentActivity.class);
                    startActivity(intent);
                }//TODO create another one for the second option
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        //prepare Data
        prepareMainOptions();

        //this code runs only once
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {

            createGUID();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }

    /**
     * create the uniqe id that identify this app instance for this particular device
     */
    private void createGUID() {
        SharedPreferences sPrefs= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sPrefs.edit();
        String uniqueID = UUID.randomUUID().toString();
        editor.putString("key_guid", uniqueID);
        editor.apply();
    }

    private void prepareMainOptions() {
        dataArrayList.add(new MainScreenData(R.drawable.needapparetment,R.string.mNeedApartment,
                R.string.mNeedApartmentDesc));
        dataArrayList.add(new MainScreenData(R.drawable.wnaarent,R.string.mWannaRent,
                R.string.mWannaRentDesc));

        mAdapter.notifyDataSetChanged();
    }
}
