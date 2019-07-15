package com.projeto.academicplanner.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfigFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth referenciaAutenticacao;
    private static StorageReference referenciaStorage;

    //retorna a referencia do database
    public static DatabaseReference getReferenciaFirebase() {
        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    //retorna referencia do FireBaseAuth
    public static FirebaseAuth getReferenciaAutenticacao() {
        if (referenciaAutenticacao == null) {
            referenciaAutenticacao = FirebaseAuth.getInstance();
        }
        return referenciaAutenticacao;
    }

    //retorna referencia do FireBaseStore
    public static StorageReference getReferenciaStorage() {
        if (referenciaStorage == null) {
            referenciaStorage = FirebaseStorage.getInstance().getReference();
        }
        return referenciaStorage;
    }

    public static String getUserId(){

        return referenciaAutenticacao.getCurrentUser().getUid();

    }

    public static String getUniversityId() {

        return referenciaFirebase.child(getUniversityId()).getKey();

    }

}
