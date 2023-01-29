package com.example.exm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goGost(View view) {
        Admin.admin=1;
        startActivity(new Intent(this,MainWindow.class));
    }

    public void goUser(View view) {
        Admin.admin=2;
        startActivity(new Intent(this,MainWindow.class));
    }
}