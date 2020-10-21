package com.example.ayman.isurvive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Videos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
    }
    public void go(View v){
        Intent intent = new Intent(Videos.this, Play.class);
        if(v.getId()==R.id.volcanoes){
            intent.putExtra("vid", "1");
        }
        else if(v.getId()==R.id.floods){
            intent.putExtra("vid", "2");
        }
        else if(v.getId()==R.id.hurricanes){
            intent.putExtra("vid", "3");
        }
        else if(v.getId()==R.id.earthquakes){
            intent.putExtra("vid", "4");
        }
        startActivity(intent);
    }
}
