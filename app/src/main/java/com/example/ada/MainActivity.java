package com.example.ada;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Database database;
    private EditText enterMedName, enterTime, enterDate;
    private Button submit, viewBtn;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterMedName = findViewById(R.id.enterMedicineName);
        enterDate = findViewById(R.id.enterDate);
        enterTime = findViewById(R.id.enterTime);
        submit = findViewById(R.id.submit);
        database = new Database(MainActivity.this);

        enterTime.setShowSoftInputOnFocus(false);
        if(!foregroundServiceRunning()){
            Intent serviceIntent = new Intent(this, MedicineService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            }
        }
        Intent serviceIntent = new Intent(this, MedicineService.class);
        startForegroundService(serviceIntent);
        submit.setOnClickListener(view -> {
            String medName = enterMedName.getText().toString();
            String medTime = enterTime.getText().toString();
            String medDate = enterDate.getText().toString();
            if(medName.isEmpty() || medTime.isEmpty() || medDate.isEmpty()){
                Toast.makeText(MainActivity.this, "Some value missing...", Toast.LENGTH_SHORT).show();
            }
            else{
                boolean insertSuccess = database.addNewMedicine(medName,medTime,medDate);
                if(insertSuccess){
                    Toast.makeText(MainActivity.this, "Medicine name added", Toast.LENGTH_SHORT).show();
                    enterTime.setText("");
                    enterMedName.setText("");
                    enterDate.setText("");
                }
                else{
                    Toast.makeText(MainActivity.this, "Nothing Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.viewBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Viewmedicines.class);
                startActivity(i);
            }
        });
    }
    // Methods
    public void onClickTime(View view) {
        final Calendar calendar = Calendar.getInstance();
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfday, int minute) {
                enterTime.setText(new StringBuilder().append(hourOfday).append(":").append(minute).toString());
                enterTime.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
            }
        },mHour,mMinute,true);
        timePickerDialog.show();
    }
    public void onClickDate(View view) {
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                enterDate.setText(new StringBuilder().append(dayOfMonth).append("/").append(monthOfYear + 1).append("/").append(year).toString());
                enterDate.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public  boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)
        ) {
            if(MedicineService.class.getName().equals(service.getClass().getName())){
                return  true;
            }
        }
        return  false;
    }
}