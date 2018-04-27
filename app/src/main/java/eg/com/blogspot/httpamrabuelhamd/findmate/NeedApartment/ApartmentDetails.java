package eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartment.singleAprtmentDataPublisher.SingleAprtmentData;
import eg.com.blogspot.httpamrabuelhamd.findmate.R;
import xyz.klinker.android.drag_dismiss.activity.DragDismissActivity;

/**
 * Created by amro mohamed on 4/22/2018.
 */

public class ApartmentDetails extends DragDismissActivity {
    private GoogleSignInClient mGoogleSignInClient;
    TextView price;
    private static final int RC_SIGN_IN = 9001;

    @Override
    public View onCreateContent(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_apartment_details, parent, false);

        //region handle sing in
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //endregion

        // do your normal view setup and Activity#onCreate lifecycle actions here,
        // instead of within the Activity#onCreate.

        //getting the selected apart data obj
        Bundle data = getIntent().getExtras();
        SingleAprtmentData aprtmentData= data.getParcelable("ApartmentData");
        Log.v("Appart2","someData= "+aprtmentData.getResultId());

        price = v.findViewById(R.id.testy);//please note i should use v before calling findvewbyid as if were a fragmnet
//        price.setText(" hgjhg "+aprtmentData.getResultId());

        //todo: call the server to get more data
        return v;
    }

    public void buttonClick_CallOwnerNumber(View view) {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            price.setText("signed in");
            //todo: start the intent of calling the owner number
            //suppose sending this status to server to get owner number
        }
        else {
            price.setText("not signed in");
            signIn();
        }
    }
    //region handle sign in  for more info check http://bit.ly/2qVvFpU
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            updateUi(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ai abn wiskgha", "signInResult:failed code=" + e.getStatusCode());
            updateUi(null);
        }
    }
    // [END handleSignInResult]
    void updateUi(GoogleSignInAccount account){
        if(account!=null){
            price.setText("signed in");
        }
        else
            price.setText("not signed in");
    }
    //endregion
}
