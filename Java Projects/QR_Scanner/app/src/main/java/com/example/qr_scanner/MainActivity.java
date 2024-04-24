package com.example.qr_scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    //Ez lesz az elso kod beolvasonk
    private CodeScanner scanner1;
    private Button butEx;
    private Button valt1;

    private static TextView kiadText;
    private static TextView beveszText;
    private static TextView ennyiText;

    public static Vehicles k1;
    public static Vehicles k2;
    public static Vehicles k3;
    public static Vehicles k4;
    public static Vehicles k5;
    public static Vehicles k6;
    public static Vehicles k7;
    public static Vehicles k8;
    public static Vehicles k9;
    public static Vehicles k10;
    public static Vehicles k11;
    public static Vehicles k12;
    public static Vehicles k13;
    public static Vehicles k14;
    public static Vehicles k15;
    public static Vehicles k16;
    public static Vehicles k17;
    public static Vehicles k18;
    public static Vehicles k19;
    public static Vehicles k20;
    public static Vehicles r1;
    public static Vehicles r2;
    public static Vehicles r3;
    public static Vehicles r4;
    public static Vehicles r5;
    public static Vehicles r6;
    public static Context con;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        con = getApplicationContext();

        k1 = new Vehicles();
        k2 = new Vehicles();
        k3 = new Vehicles();
        k4 = new Vehicles();
        k5 = new Vehicles();
        k6 = new Vehicles();
        k7 = new Vehicles();
        k8 = new Vehicles();
        k9 = new Vehicles();
        k10 = new Vehicles();
        k11 = new Vehicles();
        k12 = new Vehicles();
        k13 = new Vehicles();
        k14 = new Vehicles();
        k15 = new Vehicles();
        k16 = new Vehicles();
        k17 = new Vehicles();
        k18 = new Vehicles();
        k19 = new Vehicles();
        k20 = new Vehicles();
        r1 = new Vehicles();
        r2 = new Vehicles();
        r3 = new Vehicles();
        r4 = new Vehicles();
        r5 = new Vehicles();
        r6 = new Vehicles();

        k1.fromFile("K1.txt");
        k2.fromFile("K2.txt");
        k3.fromFile("K3.txt");
        k4.fromFile("K4.txt");
        k5.fromFile("K5.txt");
        k6.fromFile("K6.txt");
        k7.fromFile("K7.txt");
        k8.fromFile("K8.txt");
        k9.fromFile("K9.txt");
        k10.fromFile("K10.txt");
        k11.fromFile("K11.txt");
        k12.fromFile("K12.txt");
        k13.fromFile("K13.txt");
        k14.fromFile("K14.txt");
        k15.fromFile("K15.txt");
        k16.fromFile("K16.txt");
        k17.fromFile("K17.txt");
        k18.fromFile("K18.txt");
        k19.fromFile("K19.txt");
        k20.fromFile("K20.txt");
        r1.fromFile("R1.txt");
        r2.fromFile("R2.txt");
        r3.fromFile("R3.txt");
        r4.fromFile("R4.txt");
        r5.fromFile("R5.txt");
        r6.fromFile("R6.txt");

        kiadText = (TextView) findViewById(R.id.KIAD);
        beveszText = (TextView) findViewById(R.id.VISSZA);
        ennyiText = (TextView) findViewById(R.id.IDO);


        //Deklaralunk egy masik kodolvasotnezetet, amivel megekerestetjuk a jelenlegi kodbeolvasonezetet id alapjan
        CodeScannerView scannerView = (CodeScannerView) findViewById(R.id.scannerView1);
        scanner1 = new CodeScanner(this, scannerView);
        //Megkeressuk a kilepes gombot
        butEx = findViewById(R.id.buttonEx);
        scanner1.setAutoFocusEnabled(true);

        //Megkeressuk a valto gombot
        valt1 = findViewById(R.id.buttonValt1);

        valt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage2();
            }
        });


        butEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEverything();
                System.exit(0);
            }
        });

        scanner1.setDecodeCallback(new DecodeCallback() {

            // Ha megvan az eredmeny kiirja egy textben
            @Override
            public void onDecoded(@NonNull Result result) {
                kiadText.setVisibility(View.INVISIBLE);
                beveszText.setVisibility(View.INVISIBLE);
                ennyiText.setVisibility(View.INVISIBLE);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMMM-dddd HH:mm:ss");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       switch (result.getText()){
                           case "k1":{
                               k1.scan();
                               if(!k1.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k1.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k1.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k1.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k1.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k1.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k2":{
                               k2.scan();
                               if(!k2.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k2.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k2.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k2.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k2.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k2.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k3":{
                               k3.scan();
                               if(!k3.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k3.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k3.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k3.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k3.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k3.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k4":{
                               k4.scan();
                               if(!k4.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k4.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k4.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k4.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k4.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k4.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k5":{
                               k5.scan();
                               if(!k5.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k5.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k5.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k5.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k5.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k5.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k6":{
                               k6.scan();
                               if(!k6.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k6.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k6.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k6.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k6.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k6.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k7":{
                               k7.scan();
                               if(!k7.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k7.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k7.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k7.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k7.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k7.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k8":{
                               k8.scan();
                               if(!k8.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k8.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k8.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k8.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k8.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k8.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k9":{
                               k9.scan();
                               if(!k9.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k9.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k9.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k9.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k9.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k9.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k10":{
                               k10.scan();
                               if(!k10.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k10.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k10.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k10.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k10.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k10.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k11":{
                               k11.scan();
                               if(!k11.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k11.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k11.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k11.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k11.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k11.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k12":{
                               k12.scan();
                               if(!k12.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k12.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k12.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k12.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k12.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k12.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k13":{
                               k13.scan();
                               if(!k13.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k13.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k13.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k13.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k13.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k13.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k14":{
                               k14.scan();
                               if(!k14.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k14.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k14.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k14.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k14.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k14.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k15":{
                               k15.scan();
                               if(!k15.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k15.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k15.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k15.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k15.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k15.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k16":{
                               k16.scan();
                               if(!k16.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k16.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k16.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k16.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k16.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k16.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k17":{
                               k17.scan();
                               if(!k17.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k17.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k17.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k17.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k17.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k17.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k18":{
                               k18.scan();
                               if(!k18.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k18.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k18.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k18.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k18.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k18.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k19":{
                               k19.scan();
                               if(!k19.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k19.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k19.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k19.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k19.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k19.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "k20":{
                               k20.scan();
                               if(!k20.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(k20.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   k20.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(k20.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = k20.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   k20.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "r1":{
                               r1.scan();
                               if(!r1.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(r1.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   r1.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(r1.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = r1.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   r1.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "r2":{
                               r2.scan();
                               if(!r2.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(r2.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   r2.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(r2.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = r2.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   r2.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "r3":{
                               r3.scan();
                               if(!r3.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(r3.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   r3.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(r3.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = r3.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   r3.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "r4":{
                               r4.scan();
                               if(!r4.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(r4.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   r4.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(r4.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = r4.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   r4.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "r5":{
                               r5.scan();
                               if(!r5.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(r5.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   r5.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(r5.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = r5.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   r5.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                           case "r6":{
                               r6.scan();
                               if(!r6.isKiadva){
                                   kiadText.setText("Kiadva ekkor: " +formatter.format(r6.kiadasIdo));
                                   kiadText.setVisibility(View.VISIBLE);
                                   r6.isKiadva = true;
                               }else{
                                   beveszText.setText("Visszaveve: " +formatter.format(r6.visszaVetel));
                                   beveszText.setVisibility(View.VISIBLE);
                                   long[] help = r6.kivonas();
                                   ennyiText.setText("Ora: " +help[0] +" Perc: " +help[1] +" Masodperc: " +help[2]);
                                   ennyiText.setVisibility(View.VISIBLE);
                                   r6.isKiadva = false;
                               }
                               saveEverything();
                               break;
                           }
                       }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanner1.startPreview();
            }
        });
    }

    public void saveEverything(){
        k1.toFile("K1.txt");
        k2.toFile("K2.txt");
        k3.toFile("K3.txt");
        k4.toFile("K4.txt");
        k5.toFile("K5.txt");
        k6.toFile("K6.txt");
        k7.toFile("K7.txt");
        k8.toFile("K8.txt");
        k9.toFile("K9.txt");
        k10.toFile("K10.txt");
        k11.toFile("K11.txt");
        k12.toFile("K12.txt");
        k13.toFile("K13.txt");
        k14.toFile("K14.txt");
        k15.toFile("K15.txt");
        k16.toFile("K16.txt");
        k17.toFile("K17.txt");
        k18.toFile("K18.txt");
        k19.toFile("K19.txt");
        k20.toFile("K20.txt");
        r1.toFile("R1.txt");
        r2.toFile("R2.txt");
        r3.toFile("R3.txt");
        r4.toFile("R4.txt");
        r5.toFile("R5.txt");
        r6.toFile("R6.txt");
    }


    public void openPage2(){
        saveEverything();
        Intent intent1 = new Intent(this, MainActivity2.class);
        finish();
        startActivity(intent1);
    }


    //Folytatja az olvasast
    @Override
    protected void onResume() {
        super.onResume();
        scanner1.startPreview();
    }

    //Megallitja az olvasast
    @Override
    protected void onPause() {
        scanner1.releaseResources();
        super.onPause();
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}