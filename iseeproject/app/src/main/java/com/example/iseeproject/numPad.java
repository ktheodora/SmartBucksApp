package com.example.iseeproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class numPad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_pad);

        button = (Button) findViewById(R.id.button4);

        button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        HomePage.class);
                startActivity(myIntent);
            }
        });
    }
}
