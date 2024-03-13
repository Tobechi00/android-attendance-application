package com.assignment.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.time.temporal.ValueRange;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "attendance";
    private static final String TABLE_NAME = "attendance_table";
    private static final String FIRSTNAME_COL = "firstname";
    private static final String LASTNAME_COL = "lastname";
    private static final String DEPARTMENT = "department";
    private static final String ID_COL = "id";
    private static final String DATE_COL = "date_added";
    private static final String TIME_COL = "time_added";

    public DbHelper(Context context){super(context,DATABASE_NAME,null,VERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table attendance_table(firstname TEXT, lastname TEXT, department TEXT, id TEXT, date_added TEXT, time_added TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists contact_table");
    }
    public void addAttendance(String firstname,String lastname,String department,String id,String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIRSTNAME_COL,firstname);
        values.put(LASTNAME_COL,lastname);
        values.put(DEPARTMENT,department);
        values.put(ID_COL,id);
        values.put(DATE_COL,date);
        values.put(TIME_COL,time);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public ArrayList<ModelValues> getAllData(){
        ArrayList<ModelValues> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME,null);
        while (cursor.moveToNext()){
            String firstname = cursor.getString(0);
            String lastname = cursor.getString(1);
            String department = cursor.getString(2);
            String id = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);

            ModelValues modelValues = new ModelValues(firstname,lastname,department,id,date,time);

            list.add(modelValues);
        }
        return list;
    }
    public int deleteAllData(){
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.delete(TABLE_NAME,null,null);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
}
