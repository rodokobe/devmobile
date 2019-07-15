package com.projeto.academicplanner.activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projeto.academicplanner.helper.ConfigFirebase;

public class UserFirebase {


    public static String getUserId(){
        FirebaseAuth auth = ConfigFirebase.getReferenciaAutenticacao();
        return auth.getCurrentUser().getUid();
    }

    public static FirebaseUser getCurrentUser(){
        FirebaseAuth user = ConfigFirebase.getReferenciaAutenticacao();
        return user.getCurrentUser();
    }

}
