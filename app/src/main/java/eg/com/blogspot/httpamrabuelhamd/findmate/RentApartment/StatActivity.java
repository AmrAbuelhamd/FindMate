package eg.com.blogspot.httpamrabuelhamd.findmate.RentApartment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import eg.com.blogspot.httpamrabuelhamd.findmate.R;

/**
 * Created by amro mohamed on 4/27/2018.
 */

public class StatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_state);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout2), R.string.dataSent,
                Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
