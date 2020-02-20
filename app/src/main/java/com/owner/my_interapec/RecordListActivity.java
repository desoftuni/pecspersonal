package com.owner.my_interapec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class RecordListActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<RowModel> mList;
    RecordListAdapter mAdapter = null;
    ImageView imageViewIcon;
    final int REQUEST_CODE_GALLERY = 999;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // disable landscape mode in current activity
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_record_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Lista de elementos");
        init();

        final Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM CARD");
        mList.clear();;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String descript = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            mList.add(new RowModel(id,name,descript,image));
        }
        mAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecordListActivity.this, AddActivity.class);
                startActivity(i);
            }
        });

        // action for long click on items
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // dialog for update and delete
                CharSequence[] options = {"Editar","Eliminar"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(RecordListActivity.this);
                dialog.setTitle("Elija una accion");

                dialog.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0) {
                            //update
                            Cursor c =  MainActivity.sqLiteHelper.getData("SELECT Id FROM CARD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            showDialogUpdate(RecordListActivity.this,  arrID.get(position));

                        }
                        if(which==1){
                            //delete
                            Cursor c = MainActivity.sqLiteHelper.getData("SELECT * FROM CARD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });

                dialog.show();
                return true;
            }
        });

    }

    private void showDialogDelete(final int idrecord) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(RecordListActivity.this);
        dialogDelete.setTitle("Advertencia!!!");
        dialogDelete.setMessage("Eliminar registro?");
        dialogDelete.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    MainActivity.sqLiteHelper.deleteData(idrecord);
                    Toast.makeText(RecordListActivity.this,"Eliminado correctamente", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
                updateRecordList();
            }
        });
        dialogDelete.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    public void init() {
     mListView = (ListView)findViewById(R.id.lstRecdView);
     fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);
     mList = new ArrayList<>();
     mAdapter = new RecordListAdapter(this, R.layout.row_list, mList);
     mListView.setAdapter(mAdapter);
    }

    public void showDialogUpdate(final Activity activity, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.edit_dialog);

        imageViewIcon = dialog.findViewById(R.id.imagevrec);
        final EditText edtName = dialog.findViewById(R.id.edtName);
        final EditText edtDesc = dialog.findViewById(R.id.edtDesc);
        final Button updateBtn = dialog.findViewById(R.id.btnUpdate);


        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM CARD WHERE Id="+position);
        mList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            edtName.setText(name);
            String descript = cursor.getString(2);
            edtDesc.setText(descript);
            byte[] image = cursor.getBlob(3);
            imageViewIcon.setImageBitmap(BitmapFactory.decodeByteArray(image, 0,image.length));
            mList.add(new RowModel(id,name,descript,image));
        }

        // asignar ancho del dialogo
        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        // asignar alto del dialogo
        int height = (int)(activity.getResources().getDisplayMetrics().heightPixels*0.7);
        dialog.getWindow().setLayout(width,height);
        dialog.show();

        //update image
        imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        RecordListActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateBtn.setEnabled(false);
                    updateBtn.setClickable(false);
                    MainActivity.sqLiteHelper.updateData(
                            edtName.getText().toString().trim(),
                            edtDesc.getText().toString().trim(),
                            imageViewToByte(imageViewIcon),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(activity,  "Actualizacion exitosa", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
                finally {
                    updateBtn.setEnabled(true);
                    updateBtn.setClickable(true);
                }
                updateRecordList();
            }
        });
    }

    public void updateRecordList() {
        // actualizando la lista de datos
        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM CARD");
        mList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            mList.add(new RowModel(id,name,description,image));
        }
        mAdapter.notifyDataSetChanged();
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
                imageViewIcon.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
