package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.example.practice.database.DBHandler;
import com.example.practice.database.EmployeeInfo;

public class ViewEmployee extends AppCompatActivity {

    Spinner spinner;
    DBHandler dbHandler;
    ListView listView;
    String[] from;
    int[] to;
    SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);

        spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array2, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        dbHandler = new DBHandler(this);
        from = new String[]{EmployeeInfo.Employee._ID, EmployeeInfo.Employee.COLUMN_NAME_NAME, EmployeeInfo.Employee.COLUMN_NAME_TELEPHONE,
                EmployeeInfo.Employee.COLUMN_NAME_GENDER, EmployeeInfo.Employee.COLUMN_NAME_TYPE};
        to = new int[]{R.id.id, R.id.name, R.id.telephone, R.id.gender, R.id.type};
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_employee, dbHandler.searchEmployee(), from, to, 0);
        listView = findViewById(R.id.listView);
        listView.setAdapter(simpleCursorAdapter);
    }

    public void search(View view) {
        String selected = spinner.getSelectedItem().toString();
        if ("All Employees".equals(selected)) {
            from = new String[]{EmployeeInfo.Employee._ID, EmployeeInfo.Employee.COLUMN_NAME_NAME, EmployeeInfo.Employee.COLUMN_NAME_TELEPHONE,
                    EmployeeInfo.Employee.COLUMN_NAME_GENDER, EmployeeInfo.Employee.COLUMN_NAME_TYPE};
            to = new int[]{R.id.id, R.id.name, R.id.telephone, R.id.gender, R.id.type};
            simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_employee, dbHandler.searchEmployee(), from, to, 0);
            listView.setAdapter(simpleCursorAdapter);
        } else if ("Permanent Employees".equals(selected)) {
            from = new String[]{EmployeeInfo.Employee._ID, EmployeeInfo.Employee.COLUMN_NAME_NAME, EmployeeInfo.Employee.COLUMN_NAME_TELEPHONE,
                    EmployeeInfo.Employee.COLUMN_NAME_GENDER, EmployeeInfo.Employee.COLUMN_NAME_TYPE};
            to = new int[]{R.id.id, R.id.name, R.id.telephone, R.id.gender, R.id.type};
            simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_employee, dbHandler.searchEmployee("Permanent Employee"), from, to, 0);
            listView.setAdapter(simpleCursorAdapter);
        } else {
            from = new String[]{EmployeeInfo.Employee._ID, EmployeeInfo.Employee.COLUMN_NAME_NAME, EmployeeInfo.Employee.COLUMN_NAME_TELEPHONE,
                    EmployeeInfo.Employee.COLUMN_NAME_GENDER, EmployeeInfo.Employee.COLUMN_NAME_TYPE};
            to = new int[]{R.id.id, R.id.name, R.id.telephone, R.id.gender, R.id.type};
            simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_employee, dbHandler.searchEmployee("Temporary Employee"), from, to, 0);
            listView.setAdapter(simpleCursorAdapter);
        }
    }
}
