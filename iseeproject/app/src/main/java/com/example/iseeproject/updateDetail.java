package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class updateDetail extends AppCompatActivity {
        private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);

        update=(Button)findViewById(R.id.updtbtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updated();
            }
        });
    }

        private void updated()
        {
            Intent intent2 = new Intent(updateDetail.this , HomePage.class);
            startActivity(intent2);

        }
}

