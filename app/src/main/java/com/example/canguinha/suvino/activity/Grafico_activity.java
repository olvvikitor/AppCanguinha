package com.example.canguinha.suvino.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.canguinha.suvino.R;
import com.example.canguinha.suvino.config.Base64Custom;
import com.example.canguinha.suvino.config.DateCustom;
import com.example.canguinha.suvino.config.FirebaseConfig;
import com.example.canguinha.suvino.model.Movimentacao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Grafico_activity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseConfig.getFirebaseuth();
    private DatabaseReference movimentoRef;
    private DatabaseReference ref = FirebaseConfig.getFirebase();
    private List<PieEntry> saidas = new ArrayList<>();
    private List<PieEntry> entradas = new ArrayList<>();

    private Principal_acitivity principalAcitivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        PieChart pieChart = findViewById(R.id.pieChart);
        PieChart pieChart2 = findViewById(R.id.pieChartReceita);

        String emailUsuario = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.encode(emailUsuario);
        String mesAno = getIntent().getStringExtra("mesAno");

        movimentoRef = ref.child("movimentacao").child(idUsuario).child(mesAno);

        movimentoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                saidas.clear();
                Map<String, Float> map = new TreeMap<>();

                for (DataSnapshot movimentacaoSnapshot : snapshot.getChildren()) {
                    Movimentacao movimentacao = movimentacaoSnapshot.getValue(Movimentacao.class);
                    if (movimentacao != null && "d".equals(movimentacao.getTipo())){
                        String categoria = movimentacao.getCategoria();
                        float valor = movimentacao.getValor().floatValue();
                        if (map.containsKey(categoria)){
                            float somaAtual = map.get(categoria);
                            map.put(categoria, somaAtual+valor);
                        }else {
                            map.put(categoria,valor);
                        }
                    }
                }
                for (Map.Entry<String, Float> entry : map.entrySet()) {
                    saidas.add(new PieEntry(entry.getValue(), entry.getKey()));
                }

                entradas.clear();
                Map<String, Float> map2 = new TreeMap<>();

            for (DataSnapshot movimentacaoSnapshot : snapshot.getChildren()) {
                Movimentacao movimentacaoReceita = movimentacaoSnapshot.getValue(Movimentacao.class);
                if (movimentacaoReceita != null && "r".equals(movimentacaoReceita.getTipo())){
                    String categoriaReceita = movimentacaoReceita.getCategoria();
                    float valorReceita = movimentacaoReceita.getValor().floatValue();
                    if (map2.containsKey(categoriaReceita)){
                        float somaAtualReceita = map2.get(categoriaReceita);
                        map2.put(categoriaReceita, somaAtualReceita+valorReceita);
                    }else {
                        map2.put(categoriaReceita,valorReceita);
                    }
                }
            }
                for (Map.Entry<String, Float> entry : map2.entrySet()) {
                entradas.add(new PieEntry(entry.getValue(), entry.getKey()));
            }
            setupPieChart(pieChart,pieChart2);
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setupPieChart(PieChart pieChart, PieChart pieChart2) {
        PieDataSet dataSet = new PieDataSet(saidas, "Gastos por Categoria");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setCenterText("Gastos por Categoria");
        pieChart.animateY(1000);
        pieChart.invalidate();
        pieChart.setCameraDistance(23);

        PieDataSet dataSet2 = new PieDataSet(entradas, "Entradas por Categoria");
        dataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data2 = new PieData(dataSet2);
        pieChart2.setData(data2);
        pieChart2.setCenterText("Receita por Categoria");
        pieChart2.animateY(1000);
        pieChart2.invalidate();

    }
}
