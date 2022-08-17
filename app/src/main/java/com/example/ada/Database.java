package com.example.ada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "mydatabase";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "medicine";
    public static final String ID_COL = "id";
    public static final String MED_NAME = "name";
    public static final String TIME_COL = "time";
    public static final String DATE_COL = "date";
    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME +"("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MED_NAME + " TEXT,"
                + TIME_COL + " TEXT,"
                + DATE_COL + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }
    public boolean addNewMedicine(String medName, String medTime, String medDate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(MED_NAME, medName);
        content.put(TIME_COL, medTime);
        content.put(DATE_COL, medDate);

        long flag = db.insert(TABLE_NAME, null, content);
        db.close();
        return flag != -1;
    }
    public ArrayList<MedicineModal> readMedicineData(){
        SQLiteDatabase liteDatabase = this.getReadableDatabase();
        Cursor cursor = liteDatabase.rawQuery(" SELECT * FROM "+TABLE_NAME, null);
        ArrayList<MedicineModal> medicineModalArrayList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                medicineModalArrayList.add(new MedicineModal(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  medicineModalArrayList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS medicine");
        onCreate(sqLiteDatabase);
    }
}
