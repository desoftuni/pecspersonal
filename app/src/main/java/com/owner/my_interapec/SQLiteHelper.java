package com.owner.my_interapec;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String nombre, String descripcion, byte[] imagen) {
        SQLiteDatabase database = getWritableDatabase();
        String insertQuery = "INSERT INTO CARD VALUES(NULL,?,?,?)";

        SQLiteStatement statement = database.compileStatement(insertQuery);
        statement.clearBindings();

        // insert new card data
        statement.bindString(1,nombre);
        statement.bindString(2, descripcion);
        statement.bindBlob(3, imagen);

        statement.executeInsert();
        database.close();
    }

    public void updateData(String nombre, String descripcion, byte[] imagen, int id) {
        SQLiteDatabase database = getWritableDatabase();
        String updateQuery = "UPDATE CARD SET name=?, description=?, image=? WHERE Id=?";
        SQLiteStatement statement = database.compileStatement(updateQuery);

        statement.bindString(1, nombre);
        statement.bindString(2, descripcion);
        statement.bindBlob(3,imagen);
        statement.bindDouble(4,(double)id);

        statement.execute();
        database.close();
    }

    public void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String deleteQuery = "DELETE FROM CARD WHERE Id=?";
        SQLiteStatement statement = database.compileStatement(deleteQuery);

        statement.clearBindings();
        statement.bindDouble(1,(double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    public Cursor getItemById(String sql, String param) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,new String[]{param});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
