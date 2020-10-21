package com.example.ayman.isurvive;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class One extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void go(View v) {
        Intent intent = new Intent(One.this, Two.class);
        if (v.getId() == R.id.volcanoes) {
            //intent.putExtra("vid", "1");
        }
        startActivity(intent);
        /*else if(v.getId()==R.id.floods){
            intent.putExtra("vid", "2");
        }
        else if(v.getId()==R.id.hurricanes){
            intent.putExtra("vid", "3");
        }
        else if(v.getId()==R.id.earthquakes){
            intent.putExtra("vid", "4");
        }
        startActivity(intent);
    }*/
    }
}
