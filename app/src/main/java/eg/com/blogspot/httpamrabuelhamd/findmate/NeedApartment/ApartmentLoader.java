package eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartment;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartment.singleAprtmentDataPublisher.SingleAprtmentData;

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
