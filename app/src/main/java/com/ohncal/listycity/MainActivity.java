package com.ohncal.listycity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    AppCompatButton deleteButton;
    AppCompatButton confirmButton;
    AppCompatButton addButton;
    AppCompatEditText cityNameInput;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    String selectedCity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Optionally set title
        getSupportActionBar().setTitle("ListyCity");

        cityList = findViewById(R.id.city_list);
        String[] cities = {"Tel Aviv", "Kyiv", "Gaza", "Moscow"};
        dataList = new ArrayList<String>(Arrays.asList(cities));

        // may cause problem as it is using r.layout.content
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        deleteButton = findViewById(R.id.delete_city);
        confirmButton = findViewById(R.id.city_name_input_confirm);
        addButton = findViewById(R.id.add_city);
        cityNameInput = findViewById(R.id.city_name_input);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedCity = dataList.get(position);
            cityList.setItemChecked(position, true);
            //cityList.setSelection(position);
        });

        confirmButton.setOnClickListener(v -> {
            String newCity = cityNameInput.getText().toString().trim();
            if (!newCity.isEmpty()) {
                dataList.add(newCity);
                cityAdapter.notifyDataSetChanged();
                cityNameInput.setText("");
            }
        });
        // can make the textinput and confirm a conditional view
        addButton.setOnClickListener(v -> confirmButton.performClick());


        deleteButton.setOnClickListener(v -> {
            if (selectedCity != null) {
                dataList.remove(selectedCity);
                cityAdapter.notifyDataSetChanged();
                selectedCity = null;
                cityList.clearChoices();
            }
        });
    }
}