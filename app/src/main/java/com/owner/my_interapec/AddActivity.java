package com.owner.my_interapec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddActivity extends AppCompatActivity {

    private EditText et_nombre, et_descripcion;
    private Button btnAdd, btnList;
    private ImageView imgvw;
    public static SQLiteHelper sqLiteHelper;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        init();
        sqLiteHelper = new SQLiteHelper(this, "myinterapec.sqlite",null,1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS CARD(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, description VARCHAR, image BLOB)");

        imgvw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(
                        AddActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

          btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nombre = et_nombre.getText().toString().trim();
                    String descripcion = et_descripcion.getText().toString().trim();
                    btnAdd.setEnabled(false);
                    btnList.setEnabled(false);
                    btnAdd.setClickable(false);
                    btnList.setClickable(false);
                    if(nombre.isEmpty()) {
                        Toast.makeText( getApplicationContext(), "Por favor ingrese un nombre",Toast.LENGTH_SHORT).show();
                    } else if(descripcion.isEmpty()) {
                        Toast.makeText( getApplicationContext(),"Ingrese una oracion la cual incluya el nombre", Toast.LENGTH_SHORT).show();
                    } else {
                        sqLiteHelper.insertData(
                                nombre,
                                descripcion,
                                imageViewToByte(imgvw)
                        );
                        Toast.makeText(getApplicationContext(), "Agregado con exito",Toast.LENGTH_SHORT).show();
                        et_nombre.setText("");
                        et_descripcion.setText("");
                        imgvw.setImageResource(R.drawable.camera_icon);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    btnAdd.setEnabled(true);
                    btnList.setEnabled(true);
                    btnAdd.setClickable(true);
                    btnList.setClickable(true);
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent list = new Intent(AddActivity.this, RecordListActivity.class);
                startActivity(list);
                finish();
            }
        });
    }

    public byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "No tienes permiso para acceder a archivos", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgvw.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        et_nombre = (EditText)findViewById(R.id.et_imgname);
        et_descripcion = (EditText)findViewById(R.id.et_description);
        imgvw = (ImageView)findViewById(R.id.ivselectimg);
        // btnChoose = (Button)findViewById(R.id.btnselectimg);
        btnAdd = (Button)findViewById(R.id.btnadd);
        btnList = (Button)findViewById(R.id.btnreturn);
    }
}
