package com.example.canguinha.suvino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_main);
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new SimpleSlide.Builder()
                .title("Titulo1").background(R.color.black)
                .description("Descrição2").background(R.color.black)
                .image(R.drawable.um)
                .background(android.R.color.white)
                .build());
      addSlide(new SimpleSlide.Builder()
                .title("Titulo2").background(R.color.black)
                .description("Descrição2").background(R.color.black)
                .image(R.drawable.dois)
              .background(android.R.color.white)
              .build());
      addSlide(new SimpleSlide.Builder()
                .title("Titulo3").background(R.color.black)
                .description("Descrição2").background(R.color.black)
                .image(R.drawable.tres)
              .background(android.R.color.white)
              .build());
      addSlide(new SimpleSlide.Builder()
                .title("Titulo4").background(R.color.black)
                .description("Descrição2").background(R.color.black)
                .image(R.drawable.quatro)
              .background(android.R.color.white)
              .build());
    }
}