package com.example.vote123;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;


/**
 * This fragment displays a webview that allows the user to register to vote in their state.
 */
public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";
    private Firebase rootFbRef;
    private Firebase stateFbRef;
    private Firebase chosenStateFbRef;


    /**
     * Required empty public constructor.
     */
    public RegisterFragment() {
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        return v;
    }

}


//    ImageView header;
//    WebView webView;
//    CustomWebViewClient customWebViewClient;
//    Toolbar toolbar;
//    Button pollingButton;
//    Button shareButton;
//
//    private final String TAG = getClass().getName();
//    private final String URL = "http://registertovote.ca.gov";
//    private final String doneRegisteringURL = "https://covr.sos.ca.gov/Home/Confirmation";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//
//        initializeViews();
//        setClickListeners();
//        customWebViewClient = new CustomWebViewClient();
//        webView.setWebViewClient(customWebViewClient);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(URL);
//        customWebViewClient.onPageFinished(webView, doneRegisteringURL);
////        if (webView.getUrl()==doneRegisteringURL){
////            customWebViewClient.onPageFinished(webView, doneRegisteringURL);
////        }
//    }
//
//    private void initializeViews(){
//        header = (ImageView) findViewById(R.id.register_header_imageView_id);
//        webView = (WebView) findViewById(R.id.register_webView_id);
//        toolbar = (Toolbar) findViewById(R.id.register_toolbar_id);
//        pollingButton = (Button) toolbar.findViewById(R.id.register_pollingStep_button_id);
//        shareButton = (Button) toolbar.findViewById(R.id.register_share_button_id);
//    }
//
//    private void setClickListeners(){
//        pollingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RegistrationActivity.this, FindPollingActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        shareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Uri imageURI = Uri.parse("http://imgur.com/hXVOBNe"); //was  "http://i.imgur.com/hXVOBNe.png"
////                Uri imageURI = Uri.parse("http://i.imgur.com/hXVOBNe"); //was "android.resource://" + getPackageName() + "/drawable/" + "iamregisteredareyou.png"
//                String message = "I just registered to vote using Vote123! It was super easy to use and I'm excited to vote in the upcoming election.";
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("*/*");//share.setType("text/plain").setType("image/*")
//                share.putExtra(Intent.EXTRA_TEXT, message);
////                share.putExtra(Intent.EXTRA_STREAM, R.drawable.iamregisteredareyou);
//                startActivity(Intent.createChooser(share, "Spread the word so others can get out & vote on November 8, 2016!"));
//            }
//        });
//    }
//
//private class CustomWebViewClient extends WebViewClient{
//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        return super.shouldOverrideUrlLoading(view, url); //try return false;
//    }
//
//
//    @Override
//    public void onPageFinished(WebView view, String url) {
////            if (view.getUrl()==url){ //was webview.getUrl()==doneRegisteringURL
////                toolbar.setVisibility(View.VISIBLE);
////                Toast.makeText(RegistrationActivity.this, "Congrats! You're officially registered to vote in the state of California.", Toast.LENGTH_LONG).show();
////            }
//        super.onPageFinished(view, url);
//
//    }
//}


///**
// * Creates the login webview frament for the Meetup card.
// * Created by leisforkokomo on 4/20/16.
// */
//public class MeetupLoginFragment extends Fragment {
//    //region Private Variables
//    private final String TAG = getClass().getName();
//    public final String AUTH_URL = "https://secure.meetup.com/oauth2/authorize";
//    public final String TOKEN_URL = "https://secure.meetup.com/oauth2/access";
//    private WebView loginWebView;
//    private MyWebViewClient myWebViewClient;
//    //endregion
//    //region Public Variables
//    public OnSuccessfulLoginListener mListener;
//    public String accessToken;
//    //endregion
//
//    /**
//     * This method inflates the views of the fragment and creates a new instance of the webViewClient
//     * so websites are loaded inside the fragment instead of an internet browser window.
//     * This method also makes an instance of the OauthClientRequest to authenticate users of the app.
//     * This method calls the listener of the OnSuccessfulLoginLister interface.
//     * @param inflater
//     * @param container
//     * @param savedInstanceState
//     * @return
//     */
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_meetup_webview, container, false);
//        loginWebView = (WebView) v.findViewById(R.id.meetup_webView_id);//new WebView(getContext());
//        myWebViewClient = new MyWebViewClient();
//        loginWebView.setWebViewClient(myWebViewClient);
//        loginWebView.getSettings().setJavaScriptEnabled(true);
//        OAuthClientRequest request = null;
//        try {
//            request = OAuthClientRequest.authorizationLocation(
//                    AUTH_URL).setClientId(
//                    ApiKeys.MEETUP_CONSUMER_KEY).setRedirectURI(
//                    ApiKeys.MEETUP_REDIRECT_URI).buildQueryMessage();
//        } catch (OAuthSystemException e) {
//            Log.d(TAG, "OAuth request failed", e);
//            e.printStackTrace();
//        }
//        loginWebView.loadUrl(request.getLocationUri() + "&response_type=code&set_mobile=on");
//        myWebViewClient.onPageFinished(loginWebView, ApiKeys.MEETUP_REDIRECT_URI + "&client_id=" + accessToken + "&response_type=code&set_mobile=on");
//        return v;
//    }
//
//    /**
//     * This method creates an innerclass for a subclass of the WebViewClient. When the specific page
//     * is finished loading, the mListener is called to send the accessToken to the MainActivity.
//     * shouldOverrideUrlLoading verifies if the redirect uri contains an error or code.
//     */
//    private class MyWebViewClient extends WebViewClient{
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            if (accessToken != null){
//                mListener.onSuccessfulLogin(accessToken);
//                Log.i(TAG, "The access token is " + accessToken);
//            } else {
//                String crapToken = "crap";
//                Log.i(TAG, "The access token is " + crapToken);
//            }
//        }
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Uri uri = Uri.parse(url);
//            String code = uri.getQueryParameter("code");
//            String error = uri.getQueryParameter("error");
//            if (code != null){
//                new MeetupRetrieveAccessTokenTask().execute(uri);
//                Log.i(TAG, "The redirect uri contained the code " + code);
//            } else if (error != null) {
//                Log.e(TAG, "The redirect uri contained error");
//            }
//            return false;
//        }
//    }
//
//    /**
//     * This method creates an AsyncTask to handle user authentication.
//     * It can return the accessToken, expiration time, and refresh token.
//     */
//    private class MeetupRetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void>{
//        @Override
//        protected Void doInBackground(Uri... params) {
//            Uri uri = params[0];
//            String code = uri.getQueryParameter("code");
//
//            Log.e(TAG, "Code = " + code);
//
//            OAuthClientRequest request = null;
//            try {
//                request = OAuthClientRequest.tokenLocation(TOKEN_URL)
//                        .setGrantType(GrantType.AUTHORIZATION_CODE).setClientId(
//                                ApiKeys.MEETUP_CONSUMER_KEY).setClientSecret(
//                                ApiKeys.MEETUP_CONSUMER_SECRET).setRedirectURI(
//                                ApiKeys.MEETUP_REDIRECT_URI).setCode(code)
//                        .buildBodyMessage();
//                OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
//                OAuthAccessTokenResponse response = oAuthClient.accessToken(request);
//
//                Log.d(TAG, response.getAccessToken());
//                Log.d(TAG, response.getExpiresIn());
//                Log.d(TAG, response.getRefreshToken());
//                accessToken = response.getAccessToken();
//
//            } catch (OAuthSystemException e) {
//                Log.e(TAG, "Oauth System Exception - Couldn't get access token: " + e.toString());
//
//            } catch (OAuthProblemException e) {
//                Log.e(TAG, "Oauth System Exception - Couldn't get access token" + e.getMessage());
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
//
//    /**
//     * This method instantiates an instance of the OnSuccessfulLoginListener and connects it to an activity.
//     * @param context
//     */
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            mListener = (OnSuccessfulLoginListener) getActivity();
//        } catch (ClassCastException e){
//            Log.e(TAG, "Must implement OnSuccessfulLoginListener");
//        }
//    }
//
//
//}

