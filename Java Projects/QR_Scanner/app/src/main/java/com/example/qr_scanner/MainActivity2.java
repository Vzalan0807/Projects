package com.example.qr_scanner;

import static com.example.qr_scanner.Allapot.*;
import static com.example.qr_scanner.MainActivity.k1;
import static com.example.qr_scanner.MainActivity.k10;
import static com.example.qr_scanner.MainActivity.k11;
import static com.example.qr_scanner.MainActivity.k12;
import static com.example.qr_scanner.MainActivity.k13;
import static com.example.qr_scanner.MainActivity.k14;
import static com.example.qr_scanner.MainActivity.k15;
import static com.example.qr_scanner.MainActivity.k16;
import static com.example.qr_scanner.MainActivity.k17;
import static com.example.qr_scanner.MainActivity.k18;
import static com.example.qr_scanner.MainActivity.k19;
import static com.example.qr_scanner.MainActivity.k2;
import static com.example.qr_scanner.MainActivity.k20;
import static com.example.qr_scanner.MainActivity.k3;
import static com.example.qr_scanner.MainActivity.k4;
import static com.example.qr_scanner.MainActivity.k5;
import static com.example.qr_scanner.MainActivity.k6;
import static com.example.qr_scanner.MainActivity.k7;
import static com.example.qr_scanner.MainActivity.k8;
import static com.example.qr_scanner.MainActivity.k9;
import static com.example.qr_scanner.MainActivity.r1;
import static com.example.qr_scanner.MainActivity.r2;
import static com.example.qr_scanner.MainActivity.r3;
import static com.example.qr_scanner.MainActivity.r4;
import static com.example.qr_scanner.MainActivity.r5;
import static com.example.qr_scanner.MainActivity.r6;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private Button valt2;

    private TextView K1;
    private TextView K2;
    private TextView K3;
    private TextView K4;
    private TextView K5;
    private TextView K6;
    private TextView K7;
    private TextView K8;
    private TextView K9;
    private TextView K10;

    private TextView K11;
    private TextView K12;
    private TextView K13;
    private TextView K14;
    private TextView K15;
    private TextView K16;
    private TextView K17;
    private TextView K18;
    private TextView K19;
    private TextView K20;

    private TextView R1;
    private TextView R2;
    private TextView R3;
    private TextView R4;
    private TextView R5;
    private TextView R6;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        valt2 = findViewById(R.id.buttonValt2);

        K1 = (TextView) findViewById(R.id.K1);
        K2 = (TextView) findViewById(R.id.K2);
        K3 = (TextView) findViewById(R.id.K3);
        K4 = (TextView) findViewById(R.id.K4);
        K5 = (TextView) findViewById(R.id.K5);
        K6 = (TextView) findViewById(R.id.K6);
        K7 = (TextView) findViewById(R.id.K7);
        K8 = (TextView) findViewById(R.id.K8);
        K9 = (TextView) findViewById(R.id.K9);
        K10 = (TextView) findViewById(R.id.K10);
        K11 = (TextView) findViewById(R.id.K11);
        K12 = (TextView) findViewById(R.id.K12);
        K13 = (TextView) findViewById(R.id.K13);
        K14 = (TextView) findViewById(R.id.K14);
        K15 = (TextView) findViewById(R.id.K15);
        K16 = (TextView) findViewById(R.id.K16);
        K17 = (TextView) findViewById(R.id.K17);
        K18 = (TextView) findViewById(R.id.K18);
        K19 = (TextView) findViewById(R.id.K19);
        K20 = (TextView) findViewById(R.id.K20);
        R1 = (TextView) findViewById(R.id.R1);
        R2 = (TextView) findViewById(R.id.R2);
        R3 = (TextView) findViewById(R.id.R3);
        R4 = (TextView) findViewById(R.id.R4);
        R5 = (TextView) findViewById(R.id.R5);
        R6 = (TextView) findViewById(R.id.R6);

        if (k1.isKiadva) {
            K1.setVisibility(View.INVISIBLE);
        }
        if (k2.isKiadva) {
            K2.setVisibility(View.INVISIBLE);
        }
        if (k3.isKiadva) {
            K3.setVisibility(View.INVISIBLE);
        }
        if (k4.isKiadva) {
            K4.setVisibility(View.INVISIBLE);
        }
        if (k5.isKiadva) {
            K5.setVisibility(View.INVISIBLE);
        }
        if (k6.isKiadva) {
            K6.setVisibility(View.INVISIBLE);
        }
        if (k7.isKiadva) {
            K7.setVisibility(View.INVISIBLE);
        }
        if (k8.isKiadva) {
            K8.setVisibility(View.INVISIBLE);
        }
        if (k9.isKiadva) {
            K9.setVisibility(View.INVISIBLE);
        }
        if (k10.isKiadva) {
            K10.setVisibility(View.INVISIBLE);
        }
        if (k11.isKiadva) {
            K11.setVisibility(View.INVISIBLE);
        }
        if (k12.isKiadva) {
            K12.setVisibility(View.INVISIBLE);
        }
        if (k13.isKiadva) {
            K13.setVisibility(View.INVISIBLE);
        }
        if (k14.isKiadva) {
            K14.setVisibility(View.INVISIBLE);
        }
        if (k15.isKiadva) {
            K15.setVisibility(View.INVISIBLE);
        }
        if (k16.isKiadva) {
            K16.setVisibility(View.INVISIBLE);
        }
        if (k17.isKiadva) {
            K17.setVisibility(View.INVISIBLE);
        }
        if (k18.isKiadva) {
            K18.setVisibility(View.INVISIBLE);
        }
        if (k19.isKiadva) {
            K19.setVisibility(View.INVISIBLE);
        }
        if (k20.isKiadva) {
            K20.setVisibility(View.INVISIBLE);
        }
        if (r1.isKiadva) {
            R1.setVisibility(View.INVISIBLE);
        }
        if (r2.isKiadva) {
            R2.setVisibility(View.INVISIBLE);
        }
        if (r3.isKiadva) {
            R3.setVisibility(View.INVISIBLE);
        }
        if (r4.isKiadva) {
            R4.setVisibility(View.INVISIBLE);
        }
        if (r5.isKiadva) {
            R5.setVisibility(View.INVISIBLE);
        }
        if (r6.isKiadva) {
            R6.setVisibility(View.INVISIBLE);
        }

        valt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage1();
            }
        });

    }

    public void openPage1(){
        Intent intent2 = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent2);
    }

}

