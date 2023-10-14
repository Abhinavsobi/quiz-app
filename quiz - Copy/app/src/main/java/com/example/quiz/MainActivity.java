package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        B=findViewById(R.id.B1);
        B.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent next = new Intent(MainActivity.this,MainContent.class);
                startActivity(next);

            }
        });
    }

}