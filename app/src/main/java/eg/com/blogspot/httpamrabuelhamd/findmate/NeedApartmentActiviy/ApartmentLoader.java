package eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy.singleAprtmentDataPublisher.SingleAprtmentData;
import eg.com.blogspot.httpamrabuelhamd.findmate.R;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by amro mohamed on 4/17/2018.
 */

class ApartmentLoader extends AsyncTaskLoader<List<SingleAprtmentData>> {
    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link ApartmentLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public ApartmentLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * this is the background thread.
     * @return
     */
    @Override
    public List<SingleAprtmentData> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of apartments.
        List<SingleAprtmentData> apartment = UtilsNeedApartment.fetchApartmentData(mUrl);
        return apartment;

    }
}
