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
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        String data="my Info to save";

        try(FileInputStream fin = context.openFileInput("text.txt")) {
            Toast.makeText(getBaseContext(), "File exists", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            File file = new File(context.getFilesDir(), "text.txt");
            Toast.makeText(getBaseContext(), "File does not exist", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fOut = openFileOutput("text.txt", Context.MODE_PRIVATE);
            fOut.write(data.getBytes());
            fOut.close();
            Toast.makeText(getBaseContext(), "file saved", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileInputStream fin = context.openFileInput("text.txt");
            int c;
            String temp="";

            while( (c = fin.read()) != -1){
                temp = temp + c;
            }
            Toast.makeText(getBaseContext(), "file read with: " + temp, Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
        }
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dining_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }
}