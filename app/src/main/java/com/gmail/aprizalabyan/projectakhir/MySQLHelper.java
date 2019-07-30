package com.gmail.aprizalabyan.projectakhir;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class MySQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database_kontak.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE = "data";

    // Columns
    public static final String col_email = "email";
    public static final String col_name = "name";
    public static final String col_pict = "pict";

    SQLiteDatabase db;
    ContentResolver mContentResolver;

    public MySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub

        mContentResolver = context.getContentResolver();
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE + "( _id" + " integer primary key autoincrement, "
                + col_name + " text not null, "
                + col_email + " text not null, "
                + col_pict + " blob " + " );";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addToDb(String nama, String email, byte[] image){
        ContentValues cv = new  ContentValues();
        cv.put(col_name, nama);
        cv.put(col_email, email);
        cv.put(col_pict, image);
        db.insert( TABLE, null, cv );
    }

    public  void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM data WHERE _id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}
