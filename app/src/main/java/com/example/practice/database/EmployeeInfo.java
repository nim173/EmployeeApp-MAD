package com.example.practice.database;

import android.provider.BaseColumns;

public final class EmployeeInfo {
    private EmployeeInfo() {}

    public static class Employee implements BaseColumns {
        static final String TABLE_NAME = "EmployeeInfo";
        public static final String COLUMN_NAME_NAME = "empName";
        public static final String COLUMN_NAME_TELEPHONE = "empTelephone";
        public static final String COLUMN_NAME_GENDER = "empGender";
        public static final String COLUMN_NAME_TYPE = "empType";

    }
}