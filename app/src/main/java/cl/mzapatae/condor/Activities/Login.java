package cl.mzapatae.condor.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import cl.mzapatae.condor.R;
import cl.mzapatae.condor.Utils.LocalStorage;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Login extends AppCompatActivity {
    Twitter twitter;
    RequestToken requestToken = null;
    String oauth_url;
    String oauth_verifier;
    AccessToken accessToken;

    private Dialog auth_dialog;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LocalStorage.initLocalStorage(this);
        readTwitterKeys();

        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(LocalStorage.getConsumerKey(), LocalStorage.getConsumerSecret());

        Button buttonLoginTwitter = (Button) findViewById(R.id.login_buttonLogin);
        buttonLoginTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginProcess();
            }
        });

    }

    private void LoginProcess() {
        new TokenGet().execute();
    }

    private void readTwitterKeys() {

        try {
            LocalStorage.setConsumerKey(getString(R.string.twitter_consumerKey));
            LocalStorage.setConsumerSecret(getString(R.string.twitter_consumerSecret));

            Log.d("Twitter Key", "Consumer Key: " + LocalStorage.getConsumerKey());
            Log.d("Twitter Key", "Consumer Secret: " + LocalStorage.getConsumerSecret());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LoginTwitter Error", "Cant read Twitter Keys");
        }
    }

    private class TokenGet extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                requestToken = twitter.getOAuthRequestToken();
                oauth_url = requestToken.getAuthorizationURL();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return oauth_url;
        }

        @Override
        protected void onPostExecute(String oauth_url) {
            if (oauth_url != null) {
                Log.d("URL Twitter", oauth_url);
                auth_dialog = new Dialog(Login.this);
                auth_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                auth_dialog.setContentView(R.layout.webview);
                WebView web = (WebView) auth_dialog.findViewById(R.id.webview);
                web.loadUrl(oauth_url);
                web.setWebViewClient(new WebViewClient() {
                    boolean authComplete = false;

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        if (url.contains("oauth_verifier") && !authComplete) {
                            authComplete = true;
                            Log.e("Url", url);

                            Uri uri = Uri.parse(url);
                            oauth_verifier = uri.getQueryParameter("oauth_verifier");
                            auth_dialog.dismiss();
                            new AccessTokenGet().execute();
                        } else if (url.contains("denied")) {
                            auth_dialog.dismiss();
                            Toast.makeText(Login.this, getString(R.string.app_accessDenied), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                auth_dialog.show();
                auth_dialog.setCancelable(true);
            } else {
                Toast.makeText(Login.this, getString(R.string.app_invalidCredentials), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AccessTokenGet extends AsyncTask<String, String, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(Login.this);
            progress.setMessage(getString(R.string.app_fetchData));
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
        }

        @Override
        protected Boolean doInBackground(String... args) {
            try {
                accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
                LocalStorage.setOauthToken(accessToken.getToken());
                LocalStorage.setOauthSecret(accessToken.getTokenSecret());

                User user = twitter.showUser(accessToken.getUserId());
                Log.d("Condor", "Twitter user: " + user.getName() + " logued sucess");
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean response) {
            if (response) {
                progress.hide();
                LocalStorage.setLoggedInTwitter();
                Intent launchMainApplication = new Intent(Login.this, Condor.class);
                Login.this.startActivity(launchMainApplication);
                finish();
            }
        }
    }
}
