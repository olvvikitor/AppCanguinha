package com.example.canguinha.suvino.config;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseConfig {

    private static FirebaseAuth auth;

    public static FirebaseAuth getFirebaseuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
