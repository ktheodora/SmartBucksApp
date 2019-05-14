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

        Bundle b = getIntent().getExtras();
        String usr = 0; // or other values
        int Rent =0;
        int Bills =0;
        int Insurance =0;
        if(b != null)
            value = b.getText("usr_str");
            Rent = b.getInt("Rent");
            Bills = b.getInt("Bills");
        Insurwnce = b.getInt("Insurance");

        mText = (TextView)findViewById(R.id.TotalExpenses);
        mText.setText((Rent + Bills + Insurance).toString())

    }

    private void routed()
    {
        Intent intent = new Intent(HomePage.this,updateDetail.class);
        startActivity(intent);

    }
}
