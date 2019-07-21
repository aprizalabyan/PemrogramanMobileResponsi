package com.gmail.aprizalabyan.projectakhir;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database_kontak.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE = "data";

    // Columns
    public static final String number = "number";
    public static final String name = "name";
    public static final String pict = "pict";

    public MySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE + "( _id" + " integer primary key autoincrement, "
                + number + " text not null, "
                + name + " text not null, "
                + pict + " blob);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}
