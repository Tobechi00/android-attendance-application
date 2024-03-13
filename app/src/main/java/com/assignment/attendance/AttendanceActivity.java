package com.assignment.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity {
    EditText firstname;
    EditText lastname;
    Button button;
    Button backButton;
    DbHelper dbHelper;
    String tag = "AttTag";
    ProgressDialog dialog;
    String uploadUrl = "table upload route";
    String header1 = "api key";
    String header2 = "application/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        button = findViewById(R.id.submit);
        backButton = findViewById(R.id.backbutton1);
        dbHelper = new DbHelper(AttendanceActivity.this);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);
        dialog.setMessage("Inserting Data...");

        button.setOnClickListener(view -> {
            Date rawDate = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

            String refDate = df.format(rawDate);
            String refTime = tf.format(rawDate);

            String firstnamevalue = firstname.getText().toString();
            String lastnamevalue = lastname.getText().toString();
            String departmentvalue = "Backup";
            String idvalue = "11111";


            if (firstnamevalue.isEmpty()) {
                firstname.setError("this field is required");
            } else if (lastnamevalue.isEmpty()) {
                lastname.setError("this field is required");
            } else {
                try {
                    dbHelper.addAttendance(firstnamevalue, lastnamevalue, departmentvalue, idvalue, refDate, refTime);
                    insertData(firstnamevalue, lastnamevalue, departmentvalue, refDate, refTime);
                    Toast.makeText(AttendanceActivity.this, "records added successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AttendanceActivity.this, "an error has occured", Toast.LENGTH_SHORT).show();
                }
            }
        });


        backButton.setOnClickListener(view -> goBack());


    }

    private void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void insertData(String first, String last, String dept, String date, String time) {
        dialog.show();

        JSONObject records = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            JSONObject data = new JSONObject();
            JSONObject fields = new JSONObject();
            fields.put("Firstname", first);
            fields.put("Lastname", last);
            fields.put("Department", dept);
            fields.put("Date", date);
            fields.put("Time", time);
            data.put("fields", fields);
            array.put(data);
            records.put("records", array);
        } catch (Exception e) {
            Log.d(tag, "insertData" + e.getMessage());
        }
        AndroidNetworking.post(uploadUrl)
                .addHeaders("Authorization", header1)
                .addHeaders("Content-Type", header2)
                .setPriority(Priority.HIGH)
                .setTag("insertData")
                .addJSONObjectBody(records)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(tag, "onResponse" + response);
                        if (response != null) {
                            try {
                                JSONArray data = response.getJSONArray("records");
                                if (data.length() > 0) {
                                    Toast.makeText(AttendanceActivity.this, "data inserted successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AttendanceActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.d(tag, "onResponse:" + e.getMessage());
                                Toast.makeText(AttendanceActivity.this, "an exception", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AttendanceActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d(tag, "onError: " + anError.getErrorDetail());
                        Log.d(tag, "onError: " + anError.getErrorBody());
                        Toast.makeText(AttendanceActivity.this, "unable to fetch data", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
