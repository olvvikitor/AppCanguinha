package com.example.canguinha.suvino.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.canguinha.suvino.MainActivity;
import com.example.canguinha.suvino.R;
import com.example.canguinha.suvino.adapter.AdapterMovimentacao;
import com.example.canguinha.suvino.config.Base64Custom;
import com.example.canguinha.suvino.config.FirebaseConfig;
import com.example.canguinha.suvino.model.Movimentacao;
import com.example.canguinha.suvino.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Principal_acitivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private TextView saudacao, saldo;
    private DatabaseReference ref = FirebaseConfig.getFirebase();
    private FirebaseAuth auth = FirebaseConfig.getFirebaseuth();

    private DatabaseReference usuarioRef;
    private DatabaseReference movimentoRef;
    private ValueEventListener valueEventListenerUsuario;
    private ValueEventListener valueEventListenerMovimentacoes;

    private RecyclerView recyclerView;
    private AdapterMovimentacao adapterMovimentacao;
    private Double saldoTotal = 0.0;
    private List<Movimentacao> list = new ArrayList<>();
    private String mesAno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_acitivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("CANGUINHA");
        setSupportActionBar(toolbar);
        saudacao = findViewById(R.id.textSaudacao);
        saldo = findViewById(R.id.textSaldo);
        recyclerView = findViewById(R.id.recyclerMovimentos);
        calendarConfig();
        recuperarSaldo();
        adapterMovimentacao  = new AdapterMovimentacao(list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterMovimentacao);
    }
    public void recuperarMovimentacao() {
        String emailUsuario = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.encode(emailUsuario);


        // Use movimentoRef instead of usuarioRef
        movimentoRef = ref.child("movimentacoes").child(idUsuario).child(mesAno);

        valueEventListenerMovimentacoes = movimentoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dados : snapshot.getChildren()) {
                    // Assuming you have a Movimentacao constructor, adjust accordingly
                    Movimentacao movimentacao = dados.getValue(Movimentacao.class);
                    list.add(movimentacao);
                }
                // Notify the adapter that the data set has changed
                adapterMovimentacao.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("RecuperarMovimentacao", "Erro ao recuperar movimentações", error.toException());
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu );
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      if(item.getItemId() == R.id.menu_sair) {
                auth.signOut();
                startActivity(new Intent(this, Cadastro.class));
                finish();
                return true;
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
        CharSequence meses[] = {"Janeiro" ,"Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};

        calendarView = findViewById(R.id.calendarView);

        if (calendarView != null) {
            calendarView.setTitleMonths(meses);
            CalendarDay data = calendarView.getCurrentDate();
            mesAno = String.valueOf((data.getMonth()+1)+""+data.getYear());
            calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
                @Override
                public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                    mesAno = String.valueOf((+date.getMonth()+1)+""+date.getYear());
                }
            });
        } else {
            Log.e("calendarConfig", "MaterialCalendarView não foi encontrado");
        }
    }

    public void recuperarSaldo(){
        String emailUsuario = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.encode(emailUsuario);
        usuarioRef= ref.child("usuarios").child(idUsuario);
       valueEventListenerUsuario =  usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DecimalFormat formater = new DecimalFormat("0.##");
                String numeroFormatado = formater.format(saldoTotal);
                Usuario user = snapshot.getValue(Usuario.class);
                saldoTotal = user.getReceitaTotal()-user.getDespesaTotal();
                saldo.setText(numeroFormatado);
                saudacao.setText("Olá! "+user.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        recuperarSaldo();
        recuperarMovimentacao();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioRef.removeEventListener( valueEventListenerUsuario );
        movimentoRef.removeEventListener( valueEventListenerMovimentacoes );


    }


}

