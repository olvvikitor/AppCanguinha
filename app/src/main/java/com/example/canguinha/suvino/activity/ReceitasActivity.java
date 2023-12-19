package com.example.canguinha.suvino.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.canguinha.suvino.R;
import com.example.canguinha.suvino.config.Base64Custom;
import com.example.canguinha.suvino.config.DateCustom;
import com.example.canguinha.suvino.config.FirebaseConfig;
import com.example.canguinha.suvino.model.Movimentacao;
import com.example.canguinha.suvino.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ReceitasActivity extends AppCompatActivity {

    private TextInputEditText campoDate, campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference ref = FirebaseConfig.getFirebase();
    private FirebaseAuth auth = FirebaseConfig.getFirebaseuth();
    private Double receitaTotal = 0.00;
    private Double receitaGerada = 0.00;
    private Double receitaAtualizada = 0.00;
    private Spinner spinnerCategoriaReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);
        campoDate = findViewById(R.id.dataReceita);
        spinnerCategoriaReceita = findViewById(R.id.CategoriaReceita);

        String[] categorias = {"Salário", "Investimento", "Apostas", "Agiotagem"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spinnerCategoriaReceita.setAdapter(adapter);

        campoDescricao = findViewById(R.id.descricaoReceita);
        campoValor = findViewById(R.id.valorReceita);
        campoDate.setText(DateCustom.dataAtual());
        recuperarReceita();

    }
    public void salvarReceita(View view){
        if(validarReceita()==true) {
            String dateText = campoDate.getText().toString();
            movimentacao = new Movimentacao();
            movimentacao.setValor(Double.parseDouble(campoValor.getText().toString()));
            movimentacao.setCategoria(spinnerCategoriaReceita.getSelectedItem().toString());
            movimentacao.setData(campoDate.getText().toString());
            movimentacao.setTipo("r");
            movimentacao.setDescricao(campoDescricao.getText().toString());
            movimentacao.salvar(dateText);
            limparCampos();
            Toast.makeText(ReceitasActivity.this, "Receita salva.", Toast.LENGTH_SHORT).show();
            receitaGerada = movimentacao.getValor();
            receitaAtualizada = receitaGerada + receitaTotal;
            atualizarReceita(receitaAtualizada);
            abrirTelaprincipal();
            finish();

        }else{
            Toast.makeText(ReceitasActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }
    public void recuperarReceita(){
        String emailUsuario = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.encode(emailUsuario);
        DatabaseReference usuarioReference = ref.child("usuarios").child(idUsuario);
        usuarioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                receitaTotal = user.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void atualizarReceita(Double receita){
        String emailUsuario = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.encode(emailUsuario);
        DatabaseReference usuarioReference = ref.child("usuarios").child(idUsuario);
        usuarioReference.child("receitaTotal").setValue(receita);
    }


    public Boolean validarReceita() {
        if (campoDate.getText().toString().isEmpty() || campoValor.getText().toString().isEmpty() || campoDescricao.getText().toString().isEmpty() || spinnerCategoriaReceita.getSelectedItem().toString().isEmpty()) {
            return false;
        }
        return true;
    }
    public void limparCampos(){
        campoValor.setText("");
        campoDate.setText(DateCustom.dataAtual());
        campoDescricao.setText("");
    }
    public  void abrirTelaprincipal(){
        startActivity(new Intent(this, Principal_acitivity.class));
        finish();
    }
}