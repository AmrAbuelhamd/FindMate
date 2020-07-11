package eg.com.blogspot.httpamrabuelhamd.findmate.RentApartment;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by amro mohamed on 4/27/2018.
 */

public class RentApartLoader extends AsyncTaskLoader<String> {
    /** Tag for the log messages */
    private static final String LOG_TAG = RentApartLoader.class.getSimpleName();
    /** Query URL */
    private String mUrl;
    private RequestBody formbody;
    public RentApartLoader(Context context,String url,RequestBody formBody) {
        super(context);
        mUrl = url;
        this.formbody = formBody;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    String stateOfResponse="";
    /**
     * this is the background thread.
     * @return
     */
    @Override
    public /*synchronized*/ String loadInBackground() {

        /*try {
            wait(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        if (mUrl == null) {
            return null;
        }

        OkHttpClient client = new OkHttpClient();



        Request request = new Request.Builder()
                .url(mUrl)
                .post(formbody)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.code()==200)
                stateOfResponse = response.body().string();//todo parse this response to know state of the request
            else
                Log.e(LOG_TAG,"error response code: "+response.code());
        } catch (IOException e) {
            Log.e(LOG_TAG,"problem retrieving stateOfResponse from server.",e);
        }
        Log.v("emad"," "+ stateOfResponse);

        return stateOfResponse;

    }
}
