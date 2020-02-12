package com.owner.my_interapec;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    ArrayList<CardItem> list;
    public static SQLiteHelper sqLiteHelper;
    String[] values = {
            "Galeria",
            "Datos",
            "Aventura",
            "Configuracion"
            };

    int[] imagenes = {
            R.drawable.camera_icon,
            R.drawable.manager_icon,
            R.drawable.canary_islands,
            R.drawable.settings_icon
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        View cView = getLayoutInflater().inflate(R.layout.custom_action_bar,null);
        actionBar.setCustomView(cView);
//        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
//        View view = getSupportActionBar().getCustomView();

//        gridView = (GridView)findViewById(R.id.grid_menu);
//
//        GridAdapter gridAdapter = new GridAdapter(this, values, imagenes);
//        gridView.setAdapter(gridAdapter);
        sqLiteHelper = new SQLiteHelper(this,"myinterapec.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS CARD(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, description VARCHAR, image BLOB)");


        gridView = (GridView)findViewById(R.id.grid_items);
        list = new ArrayList<>();
        CardListAdapter adapter = new CardListAdapter(this,R.layout.card_items, list);
        gridView.setAdapter(adapter);

        Cursor cursor = sqLiteHelper.getData("SELECT * FROM CARD");
        list.clear();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            byte[] imagen = cursor.getBlob(3);
            list.add(new CardItem(name,description,imagen,id));
        }
        adapter.notifyDataSetChanged();
    }

   public void clickEvent(View v) {
        if(v.getId() == R.id.btn_settings) {
//            Toast.makeText(MainActivity.this,"Has clickeado en settings", Toast.LENGTH_SHORT).show();
            Intent addActivity = new Intent(MainActivity.this,MenuActivity.class);
            startActivity(addActivity);
            finish();
        }
   }
}
