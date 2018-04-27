package eg.com.blogspot.httpamrabuelhamd.findmate.RentApartment;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartment.UtilsNeedApartment;
import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartment.singleAprtmentDataPublisher.SingleAprtmentData;
import eg.com.blogspot.httpamrabuelhamd.findmate.R;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by amro mohamed on 4/27/2018.
 */

public class RentApartLoader extends AsyncTaskLoader<String> {
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
    String respond;
    /**
     * this is the background thread.
     * @return
     */
    @Override
    public String loadInBackground() {
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
            respond = response.body().string();
        } catch (IOException e) {
            Log.v("RentApartMent","e="+e);
        }
        return respond;

    }
}
