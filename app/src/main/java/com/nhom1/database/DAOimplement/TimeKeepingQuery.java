package com.nhom1.database.DAOimplement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.nhom1.constants.Constants;
import com.nhom1.database.DAO;
import com.nhom1.database.DbHelper;
import com.nhom1.database.QueryResponse;
import com.nhom1.helper.Helper;
import com.nhom1.models.Department;
import com.nhom1.models.Employee;
import com.nhom1.models.Timekeeping;
import com.nhom1.untils.MyApp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TimeKeepingQuery implements DAO.TimeKeepingQuery{
    private final DbHelper dbHelper = DbHelper.getInstance();

    @Override
    public void addTimeKeeping(String idEmployee, QueryResponse<Boolean> response) {
        Timekeeping timekeeping = new Timekeeping();
        String date = Helper.getCurrentDate();
        String uid = UUID.randomUUID().toString();
        timekeeping.set_id(uid);
        timekeeping.setDate(date);
        timekeeping.setIdEmployee(idEmployee);


        try (SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase()) {
            ContentValues contentValues = getContentValuesForTimeKeeping(timekeeping);
            long sql = sqLiteDatabase.insertOrThrow(Constants.TIMEKEEPING_TABLE, null, contentValues);
            if (sql > 0) {
                response.onSuccess(true);
                Toast.makeText(MyApp.context, "Chấm công thành công.", Toast.LENGTH_LONG).show();
            } else {
                response.onFailure("Failed to create timekeeping. Unknown Reason!");
            }
        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        }
    }

    @Override
    public void checkTimeKeepingForEmployee(String idEmployee, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        List<Department> departmentList = new ArrayList<>();
        String name_department="";
        Cursor cursor = null;
        try {
            String query = "select * from "+Constants.TIMEKEEPING_TABLE+" WHERE ("+Constants.TIMEKEEPING_ID_EMPLOYEE+"='"+idEmployee+"' AND "
                    +Constants.TIMEKEEPING_DATE+"='"+Helper.getCurrentDate()+"')";
            cursor =sqLiteDatabase.rawQuery(query,null);
            //cursor = sqLiteDatabase.query(Constants.DEPARMENT_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                response.onSuccess(true);
            } else{
                response.onFailure("Nhân viên chưa chấm công");
            }

        } catch (Exception e) {

        } finally {
            sqLiteDatabase.close();
            if (cursor != null)
                cursor.close();
        }
    }

    @Override
    public int readDateCurrentMonthOfEmployee(String idEmployee) {
        int count = 0;
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        String query = "select * from "+Constants.TIMEKEEPING_TABLE+" WHERE "+Constants.TIMEKEEPING_ID_EMPLOYEE+"='"+idEmployee+"'";
        try{
            cursor =sqLiteDatabase.rawQuery(query,null);
           // cursor = sqLiteDatabase.query(Constants.TIMEKEEPING_TABLE, null,null,null,null,null,null);
            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Timekeeping timekeeping = getTimeKeepingFromCursor(cursor);
                    if(Helper.checkDateInCurrentMonth(timekeeping.getDate())){
                        count++;
                    }

                } while (cursor.moveToNext());
            } else{}
        }catch (Exception e){
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
        return count;
    }
    private ContentValues getContentValuesForTimeKeeping(Timekeeping timekeeping) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TIMEKEEPING_ID, timekeeping.get_id());
        contentValues.put(Constants.TIMEKEEPING_DATE, timekeeping.getDate());
        contentValues.put(Constants.TIMEKEEPING_ID_EMPLOYEE, timekeeping.getIdEmployee());

        return contentValues;
    }

    private Timekeeping getTimeKeepingFromCursor(Cursor cursor){
        String _ID = cursor.getString(cursor.getColumnIndex(Constants.TIMEKEEPING_ID));
        String ID_EMPLOYEE = cursor.getString(cursor.getColumnIndex(Constants.TIMEKEEPING_ID_EMPLOYEE));
        String DATE = cursor.getString(cursor.getColumnIndex(Constants.TIMEKEEPING_DATE));


        return new Timekeeping(DATE,ID_EMPLOYEE,_ID);
    }
}
