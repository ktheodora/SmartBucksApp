package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class listActivity extends AppCompatActivity {

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Bundle b = getIntent().getExtras();

        if (b != null) {
            username = b.getString("username");
        }

        Button back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(listActivity.this, homePage.class);
                Bundle b = new Bundle();
                b.putString("username",username);

                myIntent.putExtras(b); //Put your id to your next Intent
                startActivity(myIntent);
            }
        });
    }
}
