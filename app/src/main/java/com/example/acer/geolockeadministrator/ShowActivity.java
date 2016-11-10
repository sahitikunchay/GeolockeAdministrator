package com.example.acer.geolockeadministrator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        TextView mTextView = (TextView) findViewById(R.id.textView2);
        String toDisplay = getIntent().getExtras().getString("BEACON");
        mTextView.setError(toDisplay);

    }
}
