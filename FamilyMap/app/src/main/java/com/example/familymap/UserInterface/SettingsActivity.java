package com.example.familymap.UserInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.example.familymap.R;
import com.example.familymap.model.DataCache;

public class SettingsActivity extends AppCompatActivity {

    Button logout;
    LoginFragment loginFragment;
    Switch spouseToggle, lifeStory, familyTree, paternal, maternal, male, female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataCache dataCache = DataCache.getInstance();

        super.onCreate(savedInstanceState);
        setContentView((com.example.familymap.R.layout.activity_settings));

        logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener((v) -> {
            logoutButtonClicked();
        });

        spouseToggle = (Switch) findViewById(R.id.spouse_lines);
        lifeStory = (Switch) findViewById(R.id.life_story_lines);
        familyTree = (Switch) findViewById(R.id.family_tree_lines);
        paternal = (Switch) findViewById(R.id.fathers_side);
        maternal = (Switch) findViewById(R.id.mothers_side);
        male = (Switch) findViewById(R.id.male_events);
        female = (Switch) findViewById(R.id.female_events);

        if(dataCache.spouseLine == false){
            spouseToggle.setChecked(false);
        }
        else{
            spouseToggle.setChecked(true);
        }

        spouseToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.spouseLine = true;
                }
                else {
                    dataCache.spouseLine = false;

                }
            }
        });

        if(dataCache.lifeStory == false){
            lifeStory.setChecked(false);
        }
        else{
            lifeStory.setChecked(true);
        }

        lifeStory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.lifeStory = true;
                }
                else {
                    dataCache.lifeStory = false;

                }
            }
        });

        if(dataCache.familyTree == false){
            familyTree.setChecked(false);
        }
        else{
            familyTree.setChecked(true);
        }

        familyTree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.familyTree = true;
                }
                else {
                    dataCache.familyTree = false;

                }
            }
        });

        if(dataCache.paternal == false){
            paternal.setChecked(false);
        }
        else{
            paternal.setChecked(true);
        }

        paternal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.paternal = true;
                }
                else {
                    dataCache.paternal = false;

                }
            }
        });

        if(dataCache.maternal == false){
            maternal.setChecked(false);
        }
        else{
            maternal.setChecked(true);
        }

        maternal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.maternal = true;
                }
                else {
                    dataCache.maternal = false;

                }
            }
        });

        if(dataCache.male == false){
            male.setChecked(false);
        }
        else{
            male.setChecked(true);
        }

        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.male = true;
                }
                else {
                    dataCache.male = false;

                }
            }
        });

        if(dataCache.female == false){
            female.setChecked(false);
        }
        else{
            female.setChecked(true);
        }

        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dataCache.female = true;
                }
                else {
                    dataCache.female = false;

                }
            }
        });
    }

    public void logoutButtonClicked(){

        DataCache dataCache = DataCache.getInstance();
        dataCache.clearData();

        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        //intent.putExtra("event", e.getEventID());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        ((Activity) SettingsActivity.this).overridePendingTransition(0, 0);
    }
}