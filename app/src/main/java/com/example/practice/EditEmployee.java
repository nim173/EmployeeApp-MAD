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

public class EditEmployee extends AppCompatActivity {

    DBHandler dbHandler;
    EditText name;
    EditText telephone;
    RadioGroup radioGroup;
    RadioButton male;
    RadioButton female;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        dbHandler = new DBHandler(this);
        name = findViewById(R.id.name);
        telephone = findViewById(R.id.telephone);
        radioGroup = findViewById(R.id.radioGroup);
        male = findViewById(R.id.radioButtonMale);
        female = findViewById(R.id.radioButtonFemale);
    }

    public void editEmployee(View view) {      // employee id (primary key) should not be able to be updated
        EmployeeModel employeeModel = new EmployeeModel();
        try {
            if (!("".equals(name.getText().toString()))) {
                employeeModel.setName(name.getText().toString());
                employeeModel.setTelephone(Integer.parseInt(telephone.getText().toString()));
                employeeModel.setGender(((RadioButton) findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString());
                employeeModel.setType(spinner.getSelectedItem().toString());
                if (dbHandler.updateEmployee(Integer.parseInt(((EditText) findViewById(R.id.empNo)).getText().toString()), employeeModel)) {
                    Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "!Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill in a name", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid telephone number and/or employee number", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
        }
    }

    public void searchEmployee(View view) {
        try {
            EmployeeModel employeeModel = dbHandler.searchEmployee(Integer.parseInt(((EditText) findViewById(R.id.empNo)).getText().toString()));
            if (employeeModel != null) {
                name.setText(employeeModel.getName());
                // validation for telephone done in xml - input type = number
                telephone.setText(String.valueOf(employeeModel.getTelephone()));

                if ("Male".equals(employeeModel.getGender())) {
                    male.setChecked(true);
                } else if ("Female".equals(employeeModel.getGender())) {
                    female.setChecked(true);
                } else {
                    male.setChecked(false);
                    male.setChecked(false);
                }

                if ("Permanent Employee".equals(employeeModel.getType())) {
                    spinner.setSelection(0);
                } else if ("Temporary Employee".equals(employeeModel.getType())) {
                    spinner.setSelection(1);
                }
            } else {
                Toast.makeText(this, "Invalid employee ID", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid telephone number", Toast.LENGTH_SHORT).show();
        }
    }
}
