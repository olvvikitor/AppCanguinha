package com.example.canguinha.suvino.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canguinha.suvino.R;
import com.google.firebase.auth.FirebaseAuth;

public class Cadastro extends AppCompatActivity {

    private EditText name, email, password;
    private Button cadastro;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializando os elementos de interface corretamente
        name = findViewById(R.id.nameUser);
        email = findViewById(R.id.emailUser);
        password = findViewById(R.id.passwordUserLogin);
        cadastro = findViewById(R.id.btnCadastrar);

        cadastro.setOnClickListener(view -> {
            String nname = name.getText().toString();
            String eemail = email.getText().toString();
            String ssenha = password.getText().toString();

            if (!nname.isEmpty() && !eemail.isEmpty() && !ssenha.isEmpty()) {
                // Faça algo aqui, como enviar dados para o servidor ou salvar localmente.
            } else {
                Toast.makeText(Cadastro.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });
        public void cadastrarUsuario() {
            auth = FirebaseAuth.getInstance();
        }

    }
}