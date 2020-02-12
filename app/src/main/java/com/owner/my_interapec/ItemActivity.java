package com.owner.my_interapec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    private TextView tvTag;
    private ImageView imvCard;
    public static SQLiteHelper sqLiteHelper;
    String nombre;
    String descripcion;
    byte[] imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        imvCard = (ImageView)findViewById(R.id.imv_card);
        tvTag = (TextView)findViewById(R.id.tvtagname);

        sqLiteHelper = new SQLiteHelper(this,"myinterapec.sqlite", null, 1);
        Intent inten = this.getIntent();
        String idItem = inten.getStringExtra("itemId");

        String query = "SELECT * FROM CARD WHERE Id = ?";

        Cursor c = sqLiteHelper.getItemById(query,idItem);
        if(c.moveToFirst()) {
           do {
               nombre = c.getString(1);
               descripcion = c.getString(2);
               imagen = c.getBlob(3);
           }while (c.moveToNext());
        }
        c.close();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagen,0, imagen.length);
        imvCard.setImageBitmap(bitmap);
        tvTag.setText(nombre);
    }
}
