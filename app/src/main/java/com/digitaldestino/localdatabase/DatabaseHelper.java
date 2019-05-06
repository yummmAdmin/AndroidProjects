package com.digitaldestino.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.digitaldestino.modelClass.book_table.TableData;
import com.digitaldestino.modelClass.search_dishes.SearchDish;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "saveSearchDishes";

    // table name
    private static final String TABLE_SearchDetail = "searchDishes";

    // columns name
    private static final String KEY_Name = "dish_name";
    private static final String KEY_ID = "dish_id";


    // save_quote table
    private static final String CREATE_TABLE_Dishes_Detail = "CREATE TABLE "
            + TABLE_SearchDetail + "(" + KEY_Name + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Dishes_Detail);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SearchDetail);
        onCreate(db);
    }


    // insert record
    public long insertRecord(String searchDish) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_Name, searchDish);


        long result = db.insert(TABLE_SearchDetail, null, values);
        //  db.close();
        return result;
    }


    // fetch data
    public ArrayList<String> getList() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> stringArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SearchDetail;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToLast()) {
            do {
//                SearchDish searchDish = new SearchDish();
//                searchDish.setName(cursor.getString(0));
//                stringArrayList.add(searchDish);
                stringArrayList.add(cursor.getString(0));
            } while (cursor.moveToPrevious());
        }

        return stringArrayList;
    }
}
