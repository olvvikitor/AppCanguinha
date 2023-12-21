package com.example.canguinha.suvino.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.canguinha.suvino.R;
import com.example.canguinha.suvino.config.Base64Custom;
import com.example.canguinha.suvino.config.DateCustom;
import com.example.canguinha.suvino.config.FirebaseConfig;
import com.example.canguinha.suvino.model.Movimentacao;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
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

// Importe as classes necessárias

public class Grafico_activity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseConfig.getFirebaseuth();
    private DatabaseReference movimentoRef;
    private DatabaseReference ref = FirebaseConfig.getFirebase();
    private List<PieEntry> saidas = new ArrayList<>();
    private List<PieEntry> entradas = new ArrayList<>();

    private PieChart pieChart;
    private PieChart pieChartReceita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        // Inicializa os gráficos
        pieChart = findViewById(R.id.pieChart);
        pieChartReceita = findViewById(R.id.pieChartReceita);

        setupFirebase();
    }

    private void setupFirebase() {
        String emailUsuario = auth.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.encode(emailUsuario);
        String mesAno = getIntent().getStringExtra("mesAno");

        movimentoRef = ref.child("movimentacao").child(idUsuario).child(mesAno);

        movimentoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                processarMovimentacoes(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Lidar com erro de banco de dados, se necessário
            }
        });
    }

    private void processarMovimentacoes(DataSnapshot snapshot) {
        saidas.clear();
        entradas.clear();

        // Processa as movimentações para gastos
        processarGastos(snapshot);

        // Processa as movimentações para receitas
        processarReceitas(snapshot);

        // Configuração dos gráficos
        configurarGraficos();
    }

    private void processarGastos(DataSnapshot snapshot) {
        Map<String, Float> map = new TreeMap<>();

        for (DataSnapshot movimentacaoSnapshot : snapshot.getChildren()) {
            Movimentacao movimentacao = movimentacaoSnapshot.getValue(Movimentacao.class);

            if (movimentacao != null && "d".equals(movimentacao.getTipo())) {
                String categoria = movimentacao.getCategoria();
                float valor = movimentacao.getValor().floatValue();

                if (map.containsKey(categoria)) {
                    float somaAtual = map.get(categoria);
                    map.put(categoria, somaAtual + valor);
                } else {
                    map.put(categoria, valor);
                }
            }
        }

        for (Map.Entry<String, Float> entry : map.entrySet()) {
            saidas.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
    }

    private void processarReceitas(DataSnapshot snapshot) {
        Map<String, Float> map = new TreeMap<>();

        for (DataSnapshot movimentacaoSnapshot : snapshot.getChildren()) {
            Movimentacao movimentacaoReceita = movimentacaoSnapshot.getValue(Movimentacao.class);

            if (movimentacaoReceita != null && "r".equals(movimentacaoReceita.getTipo())) {
                String categoriaReceita = movimentacaoReceita.getCategoria();
                float valorReceita = movimentacaoReceita.getValor().floatValue();

                if (map.containsKey(categoriaReceita)) {
                    float somaAtualReceita = map.get(categoriaReceita);
                    map.put(categoriaReceita, somaAtualReceita + valorReceita);
                } else {
                    map.put(categoriaReceita, valorReceita);
                }
            }
        }

        for (Map.Entry<String, Float> entry : map.entrySet()) {
            entradas.add(new PieEntry(entry.getValue(), entry.getKey()));
        }
    }

    private void configurarGraficos() {
        configurarGrafico(pieChart, saidas, "Gastos por Categoria");
        configurarGrafico(pieChartReceita, entradas, "Receita por Categoria");
    }

    private void configurarGrafico(PieChart pieChart, List<PieEntry> entries, String centerText) {
        PieDataSet dataSet = new PieDataSet(entries, centerText);
        dataSet.setSliceSpace(8f); // Espaçamento entre fatias

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.animateY(2000, Easing.EaseInExpo); // Animação
        pieChart.getDescription().setEnabled(false); // Desabilita a descrição
        pieChart.setHoleRadius(60f); // Tamanho do buraco no meio do gráfico
        pieChart.setCenterText(centerText);
        pieChart.setDrawRoundedSlices(true);
        pieChart.setRotationAngle(180); // Ângulo de rotação
        pieChart.setTransparentCircleRadius(70f); // Tamanho da borda transparente
        pieChart.setTransparentCircleColor(2);
        pieChart.setTransparentCircleAlpha(6);
        pieChart.setDrawEntryLabels(false); // Desabilita a exibição de rótulos nas fatias
        pieChart.setUsePercentValues(true); // Exibir porcentagens em vez de valores absolutos


        pieChart.invalidate();


    }
}
