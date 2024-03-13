package com.assignment.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button attendanceButton;
    Button attendeeButton;
    Button deleteButton;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        attendanceButton = findViewById(R.id.attendance);
        attendeeButton = findViewById(R.id.attendees);
        deleteButton = findViewById(R.id.delete);
        db = new DbHelper(MainActivity.this);

        attendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAttendance();
            }
        });
        attendeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendAttendee();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.deleteAllData() == 1){
                    Toast.makeText(MainActivity.this, "data cleared successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "an error has occurred data could not be deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openAttendance(){
        Intent intent = new Intent(this,AttendanceActivity.class);
        startActivity(intent);
    }
    public void opendAttendee(){
        Intent intent = new Intent(this,AttendeeActivity.class);
        startActivity(intent);
    }
}