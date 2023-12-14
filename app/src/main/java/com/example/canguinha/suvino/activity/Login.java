package com.example.canguinha.suvino.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.canguinha.suvino.R;
import com.example.canguinha.suvino.config.FirebaseConfig;
import com.example.canguinha.suvino.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class Login extends AppCompatActivity {
    private EditText campoEmail,campoSenha;
    private Button button;

    private Usuario usuario;
    private FirebaseAuth autentificacao;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail =  findViewById(R.id.editTextEmail);
        campoSenha = findViewById(R.id.editTextPassword);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if ( !textoEmail.isEmpty() ){
                    if( !textoSenha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin();
                    }else {
                        Toast.makeText(Login.this,"Preencha a Senha",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Login.this,"Preencha o Email",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validarLogin(){
        showProgressBar();
        autentificacao = FirebaseConfig.getFirebaseuth();
        autentificacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ){
                    hideProgressBar();
                    Toast.makeText(Login.this,"Sucesso ao fazer Login",Toast.LENGTH_SHORT).show();
                    abrirTelaPrincipal();
                }else{
                    String exception = "";
                    try{
                        throw task.getException();

                    } catch (FirebaseAuthInvalidUserException e){
                        exception = "Email nõ cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        exception = "Email ou senha não corresponde ao usuario cadastrado";
                    }catch (Exception e){
                        exception = "Erro ao fazer login: " + e.getMessage();
                        e.printStackTrace();
                    }


                    Toast.makeText(Login.this,exception,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, Principal_acitivity.class));
        finish();
    }
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}