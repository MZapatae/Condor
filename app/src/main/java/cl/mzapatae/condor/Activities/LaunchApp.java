package cl.mzapatae.condor.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cl.mzapatae.condor.Utils.LocalStorage;

/*
 * Created by MZapatae on 28-09-15.
 */
public class LaunchApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalStorage.initLocalStorage(this);

        if (LocalStorage.isLoggedInTwitter()) {
            Intent launchMainApplication = new Intent(LaunchApp.this, Condor.class);
            LaunchApp.this.startActivity(launchMainApplication);
            finish();
        } else {
            Intent launchLoginTwitter = new Intent(LaunchApp.this, Login.class);
            LaunchApp.this.startActivity(launchLoginTwitter);
            finish();
        }
    }
}
