package com.example.canguinha.suvino.config;

import com.google.android.gms.common.data.DataBufferRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseConfig {

    private static FirebaseAuth auth;

    private static DatabaseReference mDatabase;
    public static DatabaseReference getFirebase(){
        if (mDatabase == null){
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return mDatabase;
    }

    public static FirebaseAuth getFirebaseuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
