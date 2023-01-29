package com.example.exm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainWindow extends AppCompatActivity {
    EditText search;
    Spinner spinner;
    ListView listView;
    Button add;
    AdapterMask adapterMask;
    List<Model> modelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        add = findViewById(R.id.button);
        if(Admin.admin==1)
        {
            add.setVisibility(View.INVISIBLE);
        }
        search = findViewById(R.id.editTextTextPersonName3);
        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.listview);
        Get();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(search.getText().toString().isEmpty()){
                    Sort(modelList);
                }
                else{
                    Search();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Search();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void Back(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
    void Get(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5001/NGKNN/%D0%9C%D0%B0%D0%BC%D1%88%D0%B5%D0%B2%D0%B0%D0%AE%D0%A1/%D0%AD%D0%BA%D0%B7%D0%B0%D0%BC%D0%B5%D0%BD/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI api = retrofit.create(RetrofitAPI.class);
        Call<List<Model>> call = api.getmodel();
        call.enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                modelList = response.body();
                adapterMask = new AdapterMask(MainWindow.this,modelList);
                listView.setAdapter(adapterMask);
                adapterMask.notifyDataSetInvalidated();
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Sort(List<Model> list){
        listView.setAdapter(null);

        switch(spinner.getSelectedItemPosition()){
            case 0:
                if(search.getText().toString().isEmpty()){
                    modelList.clear();
                    Get();
                }
                break;
            case 1:
                Collections.sort(list, Comparator.comparing(Model::getCost));
                break;
            case 2:
                Collections.sort(list, Comparator.comparing(Model::getTitle));
                break;
            default:
                break;
        }
        SetAdapter(list);
    }
    public void SetAdapter(List<Model> list){
        adapterMask = new AdapterMask(MainWindow.this,list);
        listView.setAdapter(adapterMask);
        adapterMask.notifyDataSetInvalidated();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Search(){
        List<Model> lstFilter = modelList.stream().filter(x-> (x.Title.toLowerCase(Locale.ROOT).contains(search.getText().toString().toLowerCase(Locale.ROOT)))).collect(Collectors.toList());
        Sort(lstFilter);
    }

    public void addMethod(View view) {
        startActivity(new Intent(this, AddActivity.class));
    }
}