package com.example.diningdetective;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    //not busy -> 0; neutral busy -> 1; very busy -> 2
    int busyNum;
    //low food -> 0; medium food -> 1; high food -> 2
    int foodNum;
    //brittain -> 0; west -> 1; nav -> 2
    int diningHallNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeHeader("brittain.csv");
        makeHeader("west.csv");
        makeHeader("nav.csv");
        RadioGroup busyGroup = findViewById(R.id.busyGroup);
        RadioGroup foodGroup = findViewById(R.id.foodGroup);
        busyGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.notBusy) {
                busyNum = 0;
            } else  if (checkedId == R.id.neutralBusy) {
                busyNum = 1;
            } else  if (checkedId == R.id.veryBusy) {
                busyNum = 2;
            }
        });
        foodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.lowFood) {
                foodNum = 0;
            } else  if (checkedId == R.id.mediumFood) {
                foodNum = 1;
            } else  if (checkedId == R.id.highFood) {
                foodNum = 2;
            }
        });
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dining_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 0) {
                    diningHallNum = 0;
                }
                else if(position == 1) {
                    diningHallNum = 1;
                }
                else {
                    diningHallNum = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        Button submitButton = findViewById(R.id.submission);
        submitButton.setOnClickListener(v -> {
            if(busyGroup.getCheckedRadioButtonId() != -1 && foodGroup.getCheckedRadioButtonId() != -1) {
                String[] data = new String[4];
                String fileName;
                data[0] = "" + (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1);
                data[1] = "" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                data[2] = "" + busyNum;
                data[3] = "" + foodNum;
                if(diningHallNum == 0) {
                    fileName = "brittain.csv";
                } else if(diningHallNum == 1) {
                    fileName = "west.csv";
                } else {
                    fileName = "nav.csv";
                }
                addData(fileName, data[0]+","+data[1]+","+data[2]+","+data[3]);
                Toast.makeText(getBaseContext(), "Sucessfully submitted", Toast.LENGTH_SHORT).show();
            } else if(busyGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getBaseContext(), "Select how busy the dining hall is", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "Select how much food is in the dining hall", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void makeHeader(String fileName) {
        Context context = getApplicationContext();

        try(FileInputStream fin = context.openFileInput(fileName)) {
            fin.close();
            Log.d("Tag", fileName + " File exists");
        } catch (FileNotFoundException e) {
            File file = new File(context.getFilesDir(), fileName);
            Log.d("TAG", fileName + " File does not exist");
            try {
                FileOutputStream fOut = new FileOutputStream(file, true);
                fOut.write("Day,Time,Busy Level,Food Availability".getBytes());
                fOut.close();
                Log.d("TAG",fileName + " file saved");
            }
            catch (Exception d) {
                d.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fin = context.openFileInput(fileName);
            int c;
            String temp="";

            while( (c = fin.read()) != -1){
                temp = temp + ((char)c);
            }
            Log.d("TAG",fileName+" file read with: " + temp);
        }
        catch(Exception e){
            Log.d("TAG","error reading " + fileName);
        }
    }
    private void addData(String fileName, String message) {
        Context context = getApplicationContext();
        try {
            File file = new File(context.getFilesDir(), fileName);
            FileOutputStream fOut = new FileOutputStream(file, true);
            fOut.write(("\n"+message).getBytes());
            fOut.close();
            Log.d("TAG",fileName + " file saved");
        }
        catch (Exception d) {
            d.printStackTrace();
        }
        try {
            FileInputStream fin = context.openFileInput(fileName);
            int c;
            String temp="";

            while( (c = fin.read()) != -1){
                temp = temp + ((char)c);
            }
            Log.d("TAG",fileName+" file read with: " + temp);
        }
        catch(Exception e){
            Log.d("TAG","error reading " + fileName);
        }
    }
}