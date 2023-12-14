package com.example.canguinha.suvino.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.canguinha.suvino.MainActivity;
import com.example.canguinha.suvino.R;
import com.example.canguinha.suvino.config.Base64Custom;
import com.example.canguinha.suvino.config.FirebaseConfig;
import com.example.canguinha.suvino.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class Cadastro extends AppCompatActivity {

    private EditText name, email, password;
    private Button cadastro;
    private FirebaseAuth auth;
    private Usuario usuario;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializando os elementos de interface corretamente
        name = findViewById(R.id.nameUser);
        email = findViewById(R.id.emailUser);
        password = findViewById(R.id.passwordUserLogin);
        cadastro = findViewById(R.id.btnCadastrar);
        progressBar = findViewById(R.id.progressBar);

        cadastro.setOnClickListener(view -> {
            String nname = name.getText().toString();
            String eemail = email.getText().toString();
            String ssenha = password.getText().toString();

            if (!nname.isEmpty() && !eemail.isEmpty() && !ssenha.isEmpty()) {
                usuario = new Usuario();
                usuario.setNome(nname);
                usuario.setEmail(eemail);
                usuario.setSenha(ssenha);
                cadastrarUsuario();
            } else {
                Toast.makeText(Cadastro.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void cadastrarUsuario() {
        showProgressBar();
        auth = FirebaseConfig.getFirebaseuth();
        auth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressBar();
                        if(task.isSuccessful()){
                            String idUsuario = Base64Custom.encode(usuario.getEmail());
                            usuario.setIdUsuario(idUsuario);
                            usuario.salvar();

                            Toast.makeText(Cadastro.this, "Sucesso ao cadastrar usuario", Toast.LENGTH_SHORT).show();
                            abrirTelaPrincipal();
                        }else {
                            String exception ="";
                            try{
                              throw task.getException();

                            } catch (FirebaseAuthWeakPasswordException e){
                                exception = "insira uma senha mais forte";
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                exception = "Formato de email invalido";
                            } catch (FirebaseAuthUserCollisionException e){
                                exception ="Email já registrdo no sistema";
                            }catch (Exception e){
                                exception = "Erro ao cadastrar usuario: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(Cadastro.this,  exception, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public void abrirTelaPrincipal() {
        startActivity(new Intent(this, Principal_acitivity.class));
        finish();
    }
}