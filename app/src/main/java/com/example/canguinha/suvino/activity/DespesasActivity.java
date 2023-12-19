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

public class DespesasActivity extends AppCompatActivity {
    private TextInputEditText campoDate, campoCategoria, campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference ref = FirebaseConfig.getFirebase();
    private FirebaseAuth auth = FirebaseConfig.getFirebaseuth();
    private Double despesaTotal = 0.00;
    private Double despesaGerada = 0.00;
    private Double despesaAtualizada = 0.00;
    private Spinner spinnerCategoriaDespesa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas_activity);
        campoDate = findViewById(R.id.dataDespesa);
        spinnerCategoriaDespesa = findViewById(R.id.CategoriaDespesa);

        String[] categorias = {"Categoria 1", "Categoria 2", "Categoria 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spinnerCategoriaDespesa.setAdapter(adapter);

        campoDescricao = findViewById(R.id.descricaoDespesa);
        campoValor = findViewById(R.id.valorDespesa);
        campoDate.setText(DateCustom.dataAtual());
        recuperaDespesa();
    }

    public void salvarDespesa(View view){
       if(validarDespesa()==true) {
           String dateText = campoDate.getText().toString();
           movimentacao = new Movimentacao();
           movimentacao.setValor(Double.parseDouble(campoValor.getText().toString()));
           movimentacao.setCategoria(spinnerCategoriaDespesa.getSelectedItem().toString());
           movimentacao.setData(campoDate.getText().toString());
           movimentacao.setTipo("d");
           movimentacao.setDescricao(campoDescricao.getText().toString());
           movimentacao.salvar(dateText);
           limparCampos();
           Toast.makeText(DespesasActivity.this, "Despesa salva.", Toast.LENGTH_SHORT).show();
           despesaGerada = movimentacao.getValor();
           despesaAtualizada = despesaGerada+despesaTotal;
           atualizarDespesa(despesaAtualizada);
           abrirTelaprincipal();
           finish();

       }else{
               Toast.makeText(DespesasActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
           }
    }
    public void recuperaDespesa(){
        String emailUsuario = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.encode(emailUsuario);
        DatabaseReference usuarioReference = ref.child("usuarios").child(idUsuario);
        usuarioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                despesaTotal = user.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void atualizarDespesa(Double despesa){
        String emailUsuario = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.encode(emailUsuario);
        DatabaseReference usuarioReference = ref.child("usuarios").child(idUsuario);
        usuarioReference.child("despesaTotal").setValue(despesa);
    }
    public Boolean validarDespesa() {
        if (campoDate.getText().toString().isEmpty() || campoValor.getText().toString().isEmpty() || spinnerCategoriaDespesa.getSelectedItem().toString().isEmpty() || campoValor.getText().toString().isEmpty()) {
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