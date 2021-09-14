package com.example.familymap.UserInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.familymap.R;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((com.example.familymap.R.layout.activity_event));

        FragmentManager manager = this.getSupportFragmentManager();
        Fragment mapsFragment = manager.findFragmentById(R.id.eventContainer);//TODO: look at this r.id

        Intent intent = getIntent();
        String eventID = intent.getStringExtra("event");

        Intent passIntent = new Intent(EventActivity.this, MapsFragment.class);
        intent.putExtra("testVal", eventID);

        if (mapsFragment == null) {
            Bundle bundle = new Bundle();
            bundle.putString("testVal", eventID);
            mapsFragment = new MapsFragment();
            mapsFragment.setArguments(bundle);

            manager.beginTransaction()
                    .add(R.id.eventContainer, mapsFragment)
                    .commit();
        }





    }

}