package com.nhom1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nhom1.constants.Constants;
import com.nhom1.untils.MyApp;

public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper databaseHelper;
    private static final  String DATABASE_NAME = "Managerbusiness.db";


    public static DbHelper getInstance() {

        if (databaseHelper == null) {
            synchronized (DbHelper.class){ //thread safe singleton
                if (databaseHelper == null)
                    databaseHelper = new DbHelper();
            }
        }
        return databaseHelper;
    }


    public DbHelper() {
        super(MyApp.getAppContext(), DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE_DEPARTMENT);
        db.execSQL(Constants.CREATE_TABLE_EMPLOYEE);
        db.execSQL(Constants.CREATE_TABLE_TIMEKEEPING);
        db.execSQL(Constants.CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.EMPLOYEE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Constants.DEPARMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TIMEKEEPING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Constants.USER_TABLE);
        onCreate(db);
    }
}
