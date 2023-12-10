package com.example.canguinha.suvino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.canguinha.suvino.activity.Cadastro;
import com.example.canguinha.suvino.activity.Login;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new SimpleSlide.Builder()
                .background(R.color.white)
                .image(R.drawable.um)
                .canGoBackward(false)
                .canGoForward(true)
                .build());
        addSlide(new SimpleSlide.Builder()
                .image(R.drawable.dois)
                .title("Titulo2")
                .description("Descrição2")
                .background(android.R.color.white)
                .canGoBackward(true)
                .canGoForward(true)
                .build());
        addSlide(new SimpleSlide.Builder()
                .background(android.R.color.white)
                .title("Titulo3")
                .description("Descrição2")
                .image(R.drawable.tres)
                .canGoBackward(true)
                .canGoForward(true)
                .build());
        addSlide(new SimpleSlide.Builder()
                .background(android.R.color.white)
                .layout(R.layout.cadastro_)
                .build());
    }
    public void cadastrar(View view){
        startActivity(new Intent(this, Cadastro.class));

    }public void entrar(View view){
        startActivity(new Intent(this, Login.class));


    }
}
