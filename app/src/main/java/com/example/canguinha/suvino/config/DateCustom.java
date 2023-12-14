package com.example.canguinha.suvino.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;

public class DateCustom {
    public static String dataAtual(){
        long date = System.currentTimeMillis();
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = formater.format(date);
        return dataString;
    }
    public static String mesDoAno(String data){
       String mesAno[] = data.split("/");
       String mes = mesAno[1];
       String ano = mesAno[2];
       String mesAnotext = mes+ano;
        return mesAnotext;
    }
}
