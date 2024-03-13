package com.assignment.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class AttendeeActivity extends AppCompatActivity {
    CustomListAdapter adapter;
    ArrayList<ModelValues> list;
    DbHelper dbhelp;
    ListView attendanceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee);
        dbhelp = new DbHelper(AttendeeActivity.this);
        attendanceView = findViewById(R.id.attendancelist);
        loadAttendance();

    }
    private void loadAttendance(){
        list = dbhelp.getAllData();
        adapter = new CustomListAdapter(this, list);
        attendanceView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}