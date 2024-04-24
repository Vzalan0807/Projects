package com.example.qr_scanner;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Vehicles {

    char azon;

    public Date kiadasIdo;

    public Date visszaVetel;

    private Allapot all;
    public boolean isKiadva;
    private int qr;
    private String nev;

    public Vehicles( boolean iAll, int iQr, String iNev, char iAzon){

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Budapest"));

        azon = iAzon;
        kiadasIdo = null;
        visszaVetel = null;
        isKiadva = iAll;
        qr = iQr;
        nev = iNev;

    }

    public Vehicles(){
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Budapest"));
        all = Allapot.ELERHETO;
        isKiadva = false;
        kiadasIdo = null;
        visszaVetel = null;
    }

    public void setKiadasIdo(Date kiad){
        kiadasIdo = kiad;
    }

    public void setVisszaVetel(Date visszaVetel){
        this.visszaVetel = visszaVetel;
    }

    public void setAll(Allapot newA){
        all = newA;
    }

    public String getAllapot(){
        return this.all.toString();
    }

    public Date getKiad(){
        return this.kiadasIdo;
    }

    public Date getBeHoz(){
        return this.visszaVetel;
    }

    public String getNev(){
        return this.nev;
    }

    public void scan(){

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Budapest"));
        if(!isKiadva){
            kiadasIdo = new Date();
            visszaVetel = null;
            }
        else if(isKiadva) {
            visszaVetel = new Date();
        }
        }

    public long[] kivonas(){

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Budapest"));

        long eltelt = visszaVetel.getTime() - kiadasIdo.getTime();

        long sec = eltelt / 1000;
        long min = sec / 60;
        long hour = min / 60;

        sec %= 60;
        min %= 60;
        hour %= 24;

        long idok[] = new long[3];
        idok[0] = hour;
        idok[1] = min;
        idok[2] = sec;

        return idok;

    }

    public void toFile(String fileName){
        File nFile = new File(MainActivity.con.getFilesDir()+fileName);
        try {
            nFile.createNewFile();
            FileWriter writer = new FileWriter(nFile);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMMM-dddd HH:mm:ss:");
            if(isKiadva) {
                String formatted = formatter.format(kiadasIdo);
                writer.write(formatted);
                writer.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void fromFile(String fileName){
        File nFile = new File(MainActivity.con.getFilesDir()+fileName);
        try{
            nFile.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader(nFile));
            String line;
            line = br.readLine();
            if(line == null){
                this.visszaVetel =null;
                this. kiadasIdo = null;
                this.isKiadva = false;
            }else{
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMMM-dddd HH:mm:ss:");
                this.kiadasIdo = formatter.parse(line);
                this.isKiadva = true;
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}



