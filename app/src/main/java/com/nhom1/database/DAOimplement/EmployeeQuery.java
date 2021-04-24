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
import com.nhom1.models.Employee;
import com.nhom1.untils.MyApp;

import java.util.ArrayList;
import java.util.List;

public class EmployeeQuery implements DAO.EmployeeQuery {
    TimeKeepingQuery timeKeepingQuery = new TimeKeepingQuery();
    private final DbHelper dbHelper = DbHelper.getInstance();

    @Override
    public void addEmployee(Employee employee, QueryResponse<Boolean> response) {
        try (SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase()) {
            ContentValues contentValues = getContentValuesForEmployee(employee);
            long sql = sqLiteDatabase.insertOrThrow(Constants.EMPLOYEE_TABLE, null, contentValues);
            if (sql > 0) {
                response.onSuccess(true);
                Toast.makeText(MyApp.context, "Thêm mới nhân viên thành công. Mã nhân viên: " + employee.get_id(), Toast.LENGTH_LONG).show();
            } else {
                response.onFailure("Failed to create employee. Unknown Reason!");
            }
        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        }
    }

    @Override
    public void readEmployee(QueryResponse<Employee> response) {

    }

    @Override
    public void readAllEmployee(String ID_DEPARTMENT, QueryResponse<List<Employee>> response) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        List<Employee> employeeList = new ArrayList<>();

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Constants.EMPLOYEE_TABLE, null,null,null,null,null,null);
            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Employee employee = getEmployeeFromCursor(cursor);
                    employee.setWorkdays(timeKeepingQuery.readDateCurrentMonthOfEmployee(employee.get_id()));
                    if(ID_DEPARTMENT!=null ){
                        if(employee.get_idDepartment().equals(ID_DEPARTMENT))  employeeList.add(employee);
                    }else employeeList.add(employee);

                } while (cursor.moveToNext());
                DAO.DepartmentQuery departmentQuery = new DepartmentQuery();


                for(Employee item:employeeList){
                    item.setName_department(departmentQuery.readDepartment(item.get_idDepartment()));
                }
                response.onSuccess(employeeList);
            } else
                response.onFailure("There are no employee in database");
        }catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void updateEmployee(Employee employee, QueryResponse<Boolean> response) {

    }

    @Override
    public void deleteEmployee(Employee employee, QueryResponse<Boolean> response) {

    }

    @Override
    public void readAllEmployeeInDepartmet(String idDepartment, QueryResponse<List<Employee>> response) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        List<Employee> employeeList = new ArrayList<>();

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Constants.EMPLOYEE_TABLE, null,null,null,null,null,null);
            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Employee employee = getEmployeeFromCursor(cursor);

                    if(employee.get_idDepartment().equals(idDepartment)){
                        employeeList.add(employee);
                    }

                } while (cursor.moveToNext());
                response.onSuccess(employeeList);
            } else
                response.onFailure("There are no employee in database");
        }catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public int countAllEmployeeInDepartmet(String idDepartment) {
        int count = 0;
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        List<Employee> employeeList = new ArrayList<>();

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Constants.EMPLOYEE_TABLE, null,null,null,null,null,null);
            if(cursor!=null && cursor.moveToFirst()){
                do {
                    Employee employee = getEmployeeFromCursor(cursor);
                    if(employee.get_idDepartment().equals(idDepartment)) count++;
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

    private ContentValues getContentValuesForEmployee(Employee employee) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.EMPLOYEE_ID, employee.get_id());
        contentValues.put(Constants.EMPLOYEE_NAME, employee.getName());
        contentValues.put(Constants.EMPLOYEE_AVATAR, employee.getAvatar());
        contentValues.put(Constants.EMPLOYEE_SALARY, employee.getSalary());
        contentValues.put(Constants.EMPLOYEE_ID_DEPARMENT, employee.get_idDepartment());

        return contentValues;
    }

    private Employee getEmployeeFromCursor(Cursor cursor){
        String _ID = cursor.getString(cursor.getColumnIndex(Constants.EMPLOYEE_ID));
        String NAME = cursor.getString(cursor.getColumnIndex(Constants.EMPLOYEE_NAME));
        String AVATAR = cursor.getString(cursor.getColumnIndex(Constants.EMPLOYEE_AVATAR));
        String ID_DEPARTMENT = cursor.getString(cursor.getColumnIndex(Constants.EMPLOYEE_ID_DEPARMENT));
        int SALARY =cursor.getInt(cursor.getColumnIndex(Constants.EMPLOYEE_SALARY));


        return new Employee(_ID,NAME,ID_DEPARTMENT,AVATAR,0,SALARY);
    }
}



