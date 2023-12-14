package com.example.canguinha.suvino.config;

import android.util.Base64;

public class Base64Custom {
    public static String encode(String senha){

        return  Base64.encodeToString(senha.getBytes(), Base64.NO_WRAP).replaceAll("(//n|//r)", "");
    }
    public static String decode(String senha){
        return new String(Base64.decode(senha.getBytes(),Base64.DEFAULT));
    }
}
