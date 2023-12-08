package com.example.canguinha.suvino.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canguinha.suvino.R;

public class Cadastro extends AppCompatActivity {

    private EditText name, email, password;
    private Button cadastro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        name.findViewById(R.id.nameUser);
        email.findViewById(R.id.emailUser);
        password.findViewById(R.id.passwordUserLogin);

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nname = name.getText().toString();
                String eemail = email.getText().toString();
                String ssenha = password.getText().toString();

                if (!nname.isEmpty() || !eemail.isEmpty() || !ssenha.isEmpty()){

                }else{
                    Toast.makeText(Cadastro.this, "Preencha todos os campos",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}