package com.example.iseeproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
        private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
            edit =(Button)findViewById(R.id.edbtn);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    routed();
                }
            });

    }

    private void routed()
    {
        Intent intent = new Intent(HomePage.this,updateDetail.class);
        startActivity(intent);

    }
}
