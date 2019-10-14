package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.practice.database.DBHandler;
import com.example.practice.model.EmployeeModel;

public class AddEmployee extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void addEmployee(View view) {
        DBHandler dbHandler = new DBHandler(this);
        EmployeeModel employeeModel = new EmployeeModel();
        try {               // Integer range isn't enough for all telephone numbers
            if (!("".equals(((EditText)findViewById(R.id.name)).getText().toString()))) {
                employeeModel.setName(((EditText) findViewById(R.id.name)).getText().toString());
                // validation for telephone done in xml - input type = number
                employeeModel.setTelephone(Integer.parseInt(((EditText) findViewById(R.id.telephone)).getText().toString()));
                employeeModel.setGender(((RadioButton) findViewById(((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId())).getText().toString());
                employeeModel.setType(((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString());
                if (dbHandler.addEmployee(employeeModel)) {
                    Toast.makeText(this, "Employee successfully added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "!Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill in a name", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid telephone number", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
        }
    }
}
