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
import com.nhom1.helper.Helper;
import com.nhom1.models.User;
import com.nhom1.untils.MyApp;

public class UserQuery implements DAO.UserQuery {
    User user = new User();
    private final DbHelper dbHelper = DbHelper.getInstance();

    @Override
    public void addUser(String username, String password, QueryResponse<Boolean> response) {
        try (SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase()) {
            ContentValues contentValues = getContentValuesForUser(new User(username,password));
            long sql = sqLiteDatabase.insertOrThrow(Constants.TIMEKEEPING_TABLE, null, contentValues);
            if (sql > 0) {
                response.onSuccess(true);
                Toast.makeText(MyApp.context, "Insert USER to Database Success", Toast.LENGTH_LONG).show();
            } else {
                response.onFailure("Failed to create timekeeping. Unknown Reason!");
            }
        } catch (SQLiteException e) {
            response.onFailure(e.getMessage());
        }
    }

    @Override
    public void deleteUser(String username, QueryResponse<Boolean> response) {

    }

    @Override
    public User getUser() {

        return null;
    }

    private ContentValues getContentValuesForUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.USER_USERNAME, user.getUserName());
        contentValues.put(Constants.USER_PASSWORD, user.getPassWord());

        return contentValues;
    }

    private User getUserFromCursor(Cursor cursor) {
        String userName = cursor.getString(cursor.getColumnIndex(Constants.USER_USERNAME));
        String passWord = cursor.getString(cursor.getColumnIndex(Constants.USER_PASSWORD));


        return new User(userName, passWord);
    }
}
