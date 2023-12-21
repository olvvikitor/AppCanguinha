package com.example.canguinha.suvino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.canguinha.suvino.activity.Cadastro;
import com.example.canguinha.suvino.activity.Login;
import com.example.canguinha.suvino.activity.Principal_acitivity;
import com.example.canguinha.suvino.config.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {

    private FirebaseAuth autentificacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setButtonBackVisible(false);
        setButtonNextVisible(false);



        addSlide( new FragmentSlide.Builder()
                .background(R.color.inicio)
                .fragment(R.layout.intro_1)
                .build());

        addSlide( new FragmentSlide.Builder()
                .background(R.color.inicio)
                .fragment(R.layout.intro_2)
                .build());

        addSlide( new FragmentSlide.Builder()
                .background(R.color.inicio)
                .fragment(R.layout.intro_3)
                .build());

        addSlide( new FragmentSlide.Builder()
                .background(R.color.inicio)
                .fragment(R.layout.intro_4)
                .canGoForward(true)
                .build());
        addSlide( new FragmentSlide.Builder()
                .background(R.color.inicio)
                .fragment(R.layout.cadastro_)
                .canGoForward(false)
                .build());

    }

    protected void onStart(){
        super.onStart();
        verificarUsuarioLogado();
    }
    public void Cadastrar(View view){
        startActivity(new Intent(this, Cadastro.class));
    }public void Entrar(View view){
        startActivity(new Intent(this, Login.class));

    }
    public void  verificarUsuarioLogado(){
        autentificacao = FirebaseConfig.getFirebaseuth();
      // autentificacao.signOut();
                if (autentificacao.getCurrentUser() != null){
                abrirTelaPrincipal();
                }
    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, Principal_acitivity.class));
    }
}
