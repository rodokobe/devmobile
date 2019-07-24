package com.projeto.academicplanner.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.projeto.academicplanner.R;
import com.projeto.academicplanner.activity.UserFirebase;
import com.projeto.academicplanner.activity.UserProfileActivity;
import com.projeto.academicplanner.helper.ConfigFirebase;
import com.projeto.academicplanner.model.UserProfile;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileEditFragment extends Fragment {

    private FirebaseAuth auth;
    private StorageReference storageReference;
    private DatabaseReference firebaseRef;

    private TextView backToPrevious;
    private EditText firstname, lastname, emailText;
    private Button btnSave;
    private ImageView profile_image;

    private String urlImagemSelecionada;
    private String userIdLogged;

    private UserProfileFragment userProfileFragment;

    private static final int SELECAO_GALERIA = 200;


    public UserProfileEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        View v = inflater.inflate(R.layout.fragment_user_profile_edit, container, false);


        /**
         * Initializing Firebase
         */
        auth = ConfigFirebase.getReferenciaAutenticacao();
        firebaseRef = ConfigFirebase.getReferenciaFirebase();

        storageReference = ConfigFirebase.getReferenciaStorage();
        userIdLogged = UserFirebase.getUserId();


        userIdLogged = ConfigFirebase.getUserId();


        /**
         * Initializing components from activity
         */
        initializingComponents(v);
        emailText.setText(auth.getCurrentUser().getEmail());
        emailText.setEnabled(false);


        /**
         * Setting onClick action to change image profile
         */
        profile_image.setOnClickListener( view -> {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            );
            if (i.resolveActivity(getActivity().getPackageManager()) != null ){
                startActivityForResult(i, SELECAO_GALERIA);
            }
        });

        /**
         * Recovering user profile data
         */
        userDataRecovery();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSave(v);
            }
        });

        backToPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserProfileFragment();
            }
        });


        return v;
    }

    public void userDataRecovery(){
        DatabaseReference userProfileRef = firebaseRef
                .child("users")
                .child( userIdLogged );
        userProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue() != null){

                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                    firstname.setText(userProfile.getFirstname());
                    lastname.setText(userProfile.getLastname());

                    urlImagemSelecionada = userProfile.getUrlProfile();

                    if(urlImagemSelecionada != ""){
                        Picasso.get()
                                .load(urlImagemSelecionada)
                                .into(profile_image);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void validateAndSave(View view){

        String thisFirstname = firstname.getText().toString();
        String thisLastname = lastname.getText().toString();

        if (!thisFirstname.isEmpty()){
            if(!thisLastname.isEmpty()){

                UserProfile userProfile = new UserProfile();
                userProfile.setIdUser(userIdLogged);
                userProfile.setFirstname(thisFirstname);
                userProfile.setLastname(thisLastname);
                userProfile.setUrlProfile(urlImagemSelecionada);

                userProfile.save();
                toastMessageShort("Perfil alterado com sucesso");

                //getActivity().finish();
                showUserProfileFragment();
            } else {
                toastMessageShort("Digite um apelido");
            }
        } else {
            toastMessageShort("Digite um nome");

        }
    }

    private void toastMessageShort(String msg) {
        Toast.makeText(getActivity(), msg,
                Toast.LENGTH_SHORT).show();
    }

    private void toastMessageLong(String msg) {
        Toast.makeText(getActivity(), msg,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if ( resultCode == RESULT_OK){
            Bitmap imagem = null;

            try {

                switch (requestCode) {
                    case SELECAO_GALERIA:
                        Uri localImagem = data.getData();
                        imagem = MediaStore.Images
                                .Media
                                .getBitmap(
                                        getActivity().getContentResolver(),
                                        localImagem
                                );
                        break;
                }

                if (imagem != null) {

                    profile_image.setImageBitmap(imagem);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);

                    byte[] dadosImagem = baos.toByteArray();

                    StorageReference imagemRef = storageReference
                            .child("imagens")
                            .child("users")
                            .child(userIdLogged + "jpeg");

                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

                    uploadTask.addOnFailureListener(exception -> {
                        toastMessageShort("Erro ao fazer upload da imagem");
                    }).addOnSuccessListener(taskSnapshot -> {

                            imagemRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                urlImagemSelecionada = uri.toString();
                                toastMessageShort("Sucesso ao fazer upload da imagem");

                            });
                    });
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void showUserProfileFragment(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        userProfileFragment = new UserProfileFragment();
        transaction.replace(R.id.frameAddEditUserProfile, userProfileFragment);
        transaction.commit();
    }

    private void initializingComponents(View v){

        profile_image = v.findViewById(R.id.imgProfile);
        emailText = v.findViewById(R.id.editTextEmail1);
        firstname = v.findViewById(R.id.editTextFirstname);
        lastname = v.findViewById(R.id.editTextLastname);
        btnSave = v.findViewById(R.id.btnSaveUserProfileFragment);
        backToPrevious = v.findViewById(R.id.backToPrevious);

    }

}
