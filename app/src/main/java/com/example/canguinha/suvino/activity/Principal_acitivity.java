package com.example.canguinha.suvino.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.canguinha.suvino.R;
import com.example.canguinha.suvino.config.Base64Custom;
import com.example.canguinha.suvino.config.FirebaseConfig;
import com.example.canguinha.suvino.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

public class Principal_acitivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private TextView saudacao, saldo;
    private DatabaseReference ref = FirebaseConfig.getFirebase();
    private FirebaseAuth auth = FirebaseConfig.getFirebaseuth();
    private Double saldoTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_acitivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("CANGUINHA");
        setSupportActionBar(toolbar);
        saudacao = findViewById(R.id.textSaudacao);
        saldo = findViewById(R.id.textSaldo);
        recuperarSaldo();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            R.id.menu_sair:
              break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void adicionarDespesa(View view) {
        startActivity(new Intent(this, DespesasActivity.class));
    }

    public void adicionarReceita(View view) {
        startActivity(new Intent(this, ReceitasActivity.class));
    }

    public void calendarConfig(){
        CharSequence meses[] = {"Janeiro" ,"Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Desembro"};
        calendarView.setTitleMonths(meses);
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });
    }
    public void recuperarSaldo(){
        String emailUsuario = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.encode(emailUsuario);
        DatabaseReference usuarioReference = ref.child("usuarios").child(idUsuario);
        usuarioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario user = snapshot.getValue(Usuario.class);
                saldoTotal = user.getReceitaTotal()-user.getDespesaTotal();
                saldo.setText(String.valueOf(saldoTotal));
                saudacao.setText("Olá! "+user.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

