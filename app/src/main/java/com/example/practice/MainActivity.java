package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toAddEmployee(View view) {
        Intent intent = new Intent(this, AddEmployee.class);
        startActivity(intent);
    }

    public void toEditEmployee(View view) {
        Intent intent = new Intent(this, EditEmployee.class);
        startActivity(intent);
    }

    public void toViewEmployee(View view) {
        Intent intent = new Intent(this, ViewEmployee.class);
        startActivity(intent);
    }
}
