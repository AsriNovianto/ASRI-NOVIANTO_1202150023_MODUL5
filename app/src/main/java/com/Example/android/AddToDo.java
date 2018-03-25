package com.Example.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddToDo extends AppCompatActivity {
    private EditText ToDo, Description, Priority;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        setTitle("Add To Do");

        ToDo = (EditText) findViewById(R.id.edt_Todo);
        Description = (EditText) findViewById(R.id.edt_Desc);
        Priority = (EditText) findViewById(R.id.edt_Priority);
        db = new Database(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddToDo.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void addTodo(View view) {
        if (db.inputdata(new AddData(ToDo.getText().toString(), Description.getText().toString(), Priority.getText().toString()))){
            Toast.makeText(this, "List Telah diTambahkan", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddToDo.this, MainActivity.class));
            this.finish();
        }else {
            Toast.makeText(this, "List jangan ada yang kosong", Toast.LENGTH_SHORT).show();
            ToDo.setText(null);
            Description.setText(null);
            Priority.setText(null);
        }
    }

}
