package com.owner.my_interapec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MenuActivity extends AppCompatActivity {

    private Button btnTopics, btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnTopics = (Button)findViewById(R.id.btn_topics);
        btnReturn = (Button)findViewById(R.id.btn_rtn);
    }

    public void toPictures(View v) {
        Intent pictures = new Intent(this,AddActivity.class);
        startActivity(pictures);
        finish();
    }

    public void toCardList(View v) {
        Intent cardlist = new Intent(this,MainActivity.class);
        startActivity(cardlist);
        finish();
    }

}
