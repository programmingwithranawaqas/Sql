package com.example.sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText etName, etCell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etCell = findViewById(R.id.etCell);
    }

    public void btnSubmit(View view)
    {
        String name = etName.getText().toString().trim();
        String cell = etCell.getText().toString().trim();
        try{
            ContactsDB db = new ContactsDB(this);
            db.open();
            db.createEntry(name, cell);
            db.close();
        }
        catch (SQLException e)
        {
            Toast.makeText(this, "Data added.", Toast.LENGTH_SHORT).show();
        }
    }
    public void btnDelete(View view)
    {
        try{
            ContactsDB db = new ContactsDB(this);
            db.open();
            db.deleteEntry("1");
            db.close();
        }
        catch (SQLException e)
        {
            Toast.makeText(this, "Data added.", Toast.LENGTH_SHORT).show();
        }
    }
    public void btnEdit(View view)
    {
        try{
            ContactsDB db = new ContactsDB(this);
            db.open();
            db.updateEntry("2", "Waqas Ali", "123456789");
            db.close();
        }
        catch (SQLException e)
        {
            Toast.makeText(this, "Data added.", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnShowData(View view)
    {
        Intent intent = new Intent(MainActivity.this, Data.class);
        startActivity(intent);

    }
}