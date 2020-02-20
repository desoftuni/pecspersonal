package com.owner.my_interapec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class RecordListActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<RowModel> mList;
    RecordListAdapter mAdapter = null;
    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // disable landscape mode in current activity
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_record_list);
        init();

        Cursor cursor = MainActivity.sqLiteHelper.getData("SELECT * FROM CARD");
        mList.clear();;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String descript = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            mList.add(new RowModel(id,name,descript,image));
        }
        mAdapter.notifyDataSetChanged();

        // action for long click on items
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
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

                        }
                        if(which==1){
                            //delete
                        }
                    }
                });

                dialog.show();
                return true;
            }
        });

    }

    public void init() {
     mListView = (ListView)findViewById(R.id.lstRecdView);
     mList = new ArrayList<>();
     mAdapter = new RecordListAdapter(this, R.layout.row_list, mList);
     mListView.setAdapter(mAdapter);
    }
}
