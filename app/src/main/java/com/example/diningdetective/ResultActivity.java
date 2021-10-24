package com.example.diningdetective;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class ResultActivity extends AppCompatActivity {
    TextView foodText;
    TextView peopleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        foodText = findViewById(R.id.foodText);
        peopleText = findViewById(R.id.peopleText);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dining_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextSize(18);
                initPython();
                Python python = Python.getInstance();
                PyObject pythonFile = python.getModule("dining_predictor");
                String str = pythonFile.callAttr("predict").toString();
                String foodStr = str.substring(0, str.indexOf(',')) + " FOOD";
                String peopleStr = str.substring(str.indexOf(',')+2, str.length()) + " PEOPLE";
                foodText.setText(foodStr);
                peopleText.setText(peopleStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
        Button button = findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), HomeScreen.class));
            }
        });
    }

    private void initPython() {
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }
}