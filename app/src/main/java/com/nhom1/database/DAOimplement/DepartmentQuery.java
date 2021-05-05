package com.nhom1.database.DAOimplement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.nhom1.constants.Constants;
import com.nhom1.database.DAO;
import com.nhom1.database.DbHelper;
import com.nhom1.database.QueryResponse;
import com.nhom1.models.Department;
import com.nhom1.untils.MyApp;

import java.util.ArrayList;
import java.util.List;

public class DepartmentQuery implements DAO.DepartmentQuery {

    private final DbHelper dbHelper = DbHelper.getInstance();

    @Override
    public void addDepartment(Department department, QueryResponse<Boolean> response) {
        try (SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase()) {
            ContentValues contentValues = getContentValuesForDepartment(department);
            long sql = sqLiteDatabase.insertOrThrow(Constants.DEPARMENT_TABLE, null, contentValues);
            if (sql > 0) {
                response.onSuccess(true);
                Toast.makeText(MyApp.context, "Thêm mới phòng ban thành công.", Toast.LENGTH_LONG).show();
            } else {
                response.onFailure("Failed to create employee. Unknown Reason!");
            }
        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        }
    }

    @Override
    public String readDepartment(String ID_DEPARTMENT) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        List<Department> departmentList = new ArrayList<>();
        String name_department="";
        Cursor cursor = null;
        try {
            String query = "select * from "+Constants.DEPARMENT_TABLE+" WHERE "+Constants.DEPARMENT_ID+"='"+ID_DEPARTMENT+"'";
            cursor =sqLiteDatabase.rawQuery(query,null);
            //cursor = sqLiteDatabase.query(Constants.DEPARMENT_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                Department department = getDepartmentFromCursor(cursor);
                name_department = department.getName();
            } else{

            }

        } catch (Exception e) {

        } finally {
            sqLiteDatabase.close();
            if (cursor != null)
                cursor.close();
        }
        return name_department;

    }

    @Override
    public void readAllDepartment(QueryResponse<List<Department>> response) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        List<Department> departmentList = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(Constants.DEPARMENT_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Department department = getDepartmentFromCursor(cursor);
                    departmentList.add(department);
                } while (cursor.moveToNext());
                response.onSuccess(departmentList);
            } else
                response.onFailure("There are no department in database");
        } catch (Exception e) {
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if (cursor != null)
                cursor.close();
        }

    }

    @Override
    public void updateDepartment(Department department, QueryResponse<Boolean> response) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        String id = String.valueOf(department.get_id());
        ContentValues contentValues = getContentValuesForDepartment(department);
        int row = sqLiteDatabase.update(Constants.DEPARMENT_TABLE,
                contentValues,
                Constants.DEPARMENT_ID + " = ?",
                new String[]{id});
        if(row>0){
            response.onSuccess(true);
            Toast.makeText(MyApp.context, "Chỉnh sửa thông tin thành công!", Toast.LENGTH_LONG).show();
        }
        else{
            response.onFailure("Chỉnh sửa thông tin thất bại!");
        }
    }

    @Override
    public void deleteDepartment(Department department, QueryResponse<Boolean> response) {

    }

    private ContentValues getContentValuesForDepartment(Department department) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.DEPARMENT_ID, department.get_id());
        contentValues.put(Constants.DEPARMENT_NAME, department.getName());
        contentValues.put(Constants.DEPARMENT_AVATAR, department.getAvatar());

        return contentValues;
    }

    private Department getDepartmentFromCursor(Cursor cursor) {
        String _ID = cursor.getString(cursor.getColumnIndex(Constants.DEPARMENT_ID));
        String NAME = cursor.getString(cursor.getColumnIndex(Constants.DEPARMENT_NAME));
        String AVATAR = cursor.getString(cursor.getColumnIndex(Constants.DEPARMENT_AVATAR));
        int COUNT_EMPLOYEE = countEmployeeInDepartment(_ID);

        return new Department(_ID, NAME, AVATAR, COUNT_EMPLOYEE);
    }

    private int countEmployeeInDepartment(String idDeparment) {
        int count = 0;
        DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
        count = employeeQuery.countAllEmployeeInDepartmet(idDeparment);

        return count;
    }
}
