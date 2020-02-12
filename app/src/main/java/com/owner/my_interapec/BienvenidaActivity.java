package com.owner.my_interapec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class BienvenidaActivity extends AppCompatActivity {

    private ViewPager mPager;
    private int[] layouts = {R.layout.primer_slide,R.layout.segundo_slide,R.layout.tercer_slide,
                            R.layout.cuarto_slide,R.layout.quinto_slide};
    private MpagerAdapter mpagerAdapter;
    public static SQLiteHelper sqLiteHelper;
    String[] frutas = {
            "Manzana",
            "Banana",
            "Uvas",
            "Sandia",
            "Naranja",
            "Piña" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        mPager = (ViewPager)findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);

        sqLiteHelper = new SQLiteHelper(this,"myinterapec.sqlite",null,1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS CARD(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, description VARCHAR, image BLOB)");
        sqLiteHelper.insertData(frutas[0],"una manzana",imageViewToByte(R.drawable.manzana_ic));
        sqLiteHelper.insertData(frutas[1],"una banana",imageViewToByte(R.drawable.banana_ic));
        sqLiteHelper.insertData(frutas[2],"unas uvas",imageViewToByte(R.drawable.uvas_ic));
        sqLiteHelper.insertData(frutas[3],"una sandia",imageViewToByte(R.drawable.sandia_ic));
        sqLiteHelper.insertData(frutas[4],"son naranjas",imageViewToByte(R.drawable.naranja_ic));
        sqLiteHelper.insertData(frutas[5],"una piña",imageViewToByte(R.drawable.pina_ic));
    }

    public void Guardar(View view) {
        SharedPreferences preferences = getSharedPreferences("datousuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = preferences.edit();
        obj_editor.putString("isFirst","yes");
        obj_editor.commit();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public byte[] imageViewToByte(int image) {
        // get image from drawable folder
        Bitmap imagen = BitmapFactory.decodeResource(getResources(), image);
        // convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte imageInByte[] = stream.toByteArray();
        return imageInByte;
    }
}
