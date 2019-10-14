package com.example.practice.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.practice.model.EmployeeModel;

import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "emp.db";

    static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + EmployeeInfo.Employee.TABLE_NAME + "(" +
            EmployeeInfo.Employee._ID + " INTEGER PRIMARY KEY," +
            EmployeeInfo.Employee.COLUMN_NAME_NAME + " TEXT," +
            EmployeeInfo.Employee.COLUMN_NAME_TELEPHONE + " INTEGER," +
            EmployeeInfo.Employee.COLUMN_NAME_GENDER + " TEXT," +
            EmployeeInfo.Employee.COLUMN_NAME_TYPE + " TEXT)";

    static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + EmployeeInfo.Employee.TABLE_NAME;

    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }

    public boolean addEmployee(EmployeeModel employeeModel) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EmployeeInfo.Employee.COLUMN_NAME_NAME, employeeModel.getName());
        contentValues.put(EmployeeInfo.Employee.COLUMN_NAME_TELEPHONE, employeeModel.getTelephone());
        contentValues.put(EmployeeInfo.Employee.COLUMN_NAME_GENDER, employeeModel.getGender());
        contentValues.put(EmployeeInfo.Employee.COLUMN_NAME_TYPE, employeeModel.getType());

        return sqLiteDatabase.insert(EmployeeInfo.Employee.TABLE_NAME, null, contentValues) >= 1;
    }

    public boolean updateEmployee(int id, EmployeeModel employeeModel) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EmployeeInfo.Employee.COLUMN_NAME_NAME, employeeModel.getName());
        contentValues.put(EmployeeInfo.Employee.COLUMN_NAME_TELEPHONE, employeeModel.getTelephone());
        contentValues.put(EmployeeInfo.Employee.COLUMN_NAME_GENDER, employeeModel.getGender());
        contentValues.put(EmployeeInfo.Employee.COLUMN_NAME_TYPE, employeeModel.getType());

        String selection = EmployeeInfo.Employee._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        return sqLiteDatabase.update(EmployeeInfo.Employee.TABLE_NAME, contentValues, selection, selectionArgs) >= 1;
    }

    public Cursor searchEmployee() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String[] columns = {EmployeeInfo.Employee._ID, EmployeeInfo.Employee.COLUMN_NAME_NAME, EmployeeInfo.Employee.COLUMN_NAME_TELEPHONE,
                            EmployeeInfo.Employee.COLUMN_NAME_GENDER, EmployeeInfo.Employee.COLUMN_NAME_TYPE};
        String orderBy = EmployeeInfo.Employee._ID + " ASC";

        return sqLiteDatabase.query(EmployeeInfo.Employee.TABLE_NAME, columns, null, null,
                null, null, orderBy);
    }

    public Cursor searchEmployee(String type) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String[] columns = {EmployeeInfo.Employee._ID, EmployeeInfo.Employee.COLUMN_NAME_NAME, EmployeeInfo.Employee.COLUMN_NAME_TELEPHONE,
                EmployeeInfo.Employee.COLUMN_NAME_GENDER, EmployeeInfo.Employee.COLUMN_NAME_TYPE};
        String selection = EmployeeInfo.Employee.COLUMN_NAME_TYPE + " = ?";
        String[] selectionArgs = {type};
        String orderBy = EmployeeInfo.Employee._ID + " ASC";

        return sqLiteDatabase.query(EmployeeInfo.Employee.TABLE_NAME, columns, selection, selectionArgs,
                null, null, orderBy);
    }

    public EmployeeModel searchEmployee(int id) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String[] columns = {EmployeeInfo.Employee._ID, EmployeeInfo.Employee.COLUMN_NAME_NAME, EmployeeInfo.Employee.COLUMN_NAME_TELEPHONE,
                EmployeeInfo.Employee.COLUMN_NAME_GENDER, EmployeeInfo.Employee.COLUMN_NAME_TYPE};
        String selection = EmployeeInfo.Employee._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        String orderBy = EmployeeInfo.Employee._ID + " ASC";

        Cursor cursor = sqLiteDatabase.query(EmployeeInfo.Employee.TABLE_NAME, columns, selection, selectionArgs,
                null, null, orderBy);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            EmployeeModel employeeModel = new EmployeeModel();
            employeeModel.setId(id);
            employeeModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(EmployeeInfo.Employee.COLUMN_NAME_NAME)));
            employeeModel.setTelephone(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(EmployeeInfo.Employee.COLUMN_NAME_TELEPHONE))));
            employeeModel.setGender(cursor.getString(cursor.getColumnIndexOrThrow(EmployeeInfo.Employee.COLUMN_NAME_GENDER)));
            employeeModel.setType(cursor.getString(cursor.getColumnIndexOrThrow(EmployeeInfo.Employee.COLUMN_NAME_TYPE)));
            cursor.close();
            return employeeModel;
        } else {
            cursor.close();
            return null;
        }
    }
}
