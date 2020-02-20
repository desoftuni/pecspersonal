package com.owner.my_interapec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnTopics, btnCnfg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnTopics = (Button)findViewById(R.id.btn_topics);
        btnCnfg = (Button)findViewById(R.id.btnconfig);

        btnCnfg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void toPictures(View v) {
        Intent pictures = new Intent(this, RecordListActivity.class);
        startActivity(pictures);
    }
}
