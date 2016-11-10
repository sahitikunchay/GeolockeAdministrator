package com.example.acer.geolockeadministrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuildingAndLevelSelectionActivity extends AppCompatActivity {

    ExpandableListView mExpandableListView;
    ArrayList<String> building;
    HashMap<String, List<String>> level;
    ExpandableListAdapter mExpandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_and_level_selection);
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        putDataInList();

        mExpandableListAdapter = new ExpandableListAdapter(this, building, level);
        mExpandableListView.setAdapter(mExpandableListAdapter);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView pExpandableListView, View pView, int pI, int pI1, long pL) {
                //here, go to next activity with level no. and show existing config
                Toast.makeText(getApplicationContext(), building.get(pI)+" - "+ level.get(building.get(pI)).get(pI1), Toast.LENGTH_SHORT).show();
                String[] buildingSplit = building.get(pI).split(":");
                String[] levelSplit = level.get(building.get(pI)).get(pI1).split(":");
                Intent mIntent = new Intent(BuildingAndLevelSelectionActivity.this, LevelPlanWithConfiguredBeaconsActivity.class);
                mIntent.putExtra("Building", Integer.parseInt(buildingSplit[1]));
                mIntent.putExtra("Level", Integer.parseInt(levelSplit[1]));
                startActivity(mIntent);
                return true;
            }
        });
    }

    public void putDataInList(){
        building = new ArrayList<String>();
        level = new HashMap<String, List<String>>();

        // Adding child data
        building.add("Spencers, Ansal Mall, Greater Noida :23");
        building.add("Spencers, GIP, Noida :34");
        building.add("Spencers, DLF MOI, Noida :78");

        // Adding child data
        List<String> spencersAnsalMallGreaterNoida = new ArrayList<String>();
        spencersAnsalMallGreaterNoida.add("Level :1");
        spencersAnsalMallGreaterNoida.add("Level :2");

        List<String> spencersGIPNoida = new ArrayList<String>();
        spencersGIPNoida.add("Level :0");
        spencersGIPNoida.add("Level :1");
        spencersGIPNoida.add("Level :2");
        spencersGIPNoida.add("Level :3");

        List<String> spencersDLFMOINoida = new ArrayList<String>();
        spencersDLFMOINoida.add("Level :0");

        level.put(building.get(0), spencersAnsalMallGreaterNoida); // Header, Child data
        level.put(building.get(1), spencersGIPNoida);
        level.put(building.get(2), spencersDLFMOINoida);
    }
}
