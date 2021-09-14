package com.example.familymap.UserInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.familymap.R;
import com.example.familymap.net.ServerProxy;
import com.example.familymap.shared.requests.LoginRequest;
import com.example.familymap.shared.results.LoginResult;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.net.URL;

public class MainActivity extends AppCompatActivity { //AppCompatActivity

    private static final String TAG = "MainActivity";
    LoginFragment loginFragment;
    Fragment mapFragment;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(bundle) called");
        setContentView(R.layout.activity_main);
        Iconify.with(new FontAwesomeModule());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FragmentManager manager = this.getSupportFragmentManager();

        loginFragment = (LoginFragment)manager.findFragmentById(R.id.fragmentContainer);

        if (loginFragment == null) {
            loginFragment = new LoginFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, loginFragment)
                    .commit();
        }
    }

    public void onSignIn() {
        FragmentManager manager = this.getSupportFragmentManager();
        Fragment fragment2 = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment2 == null) {
            fragment2 = new MapsFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment2)
                    .commit();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }


}