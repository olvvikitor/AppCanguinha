package com.example.canguinha.suvino.model;

import com.example.canguinha.suvino.config.Base64Custom;
import com.example.canguinha.suvino.config.DateCustom;
import com.example.canguinha.suvino.config.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimentacao {
    private String data;
    private String categoria;
    private String descricao;
    private String tipo;
    private Double valor;

    public Movimentacao(String data, String categoria, String descricao, String tipo, Double valor) {
        this.data = data;
        this.categoria = categoria;
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
    }

    public void salvar(String dataEscolhida){
        FirebaseAuth auth = FirebaseConfig.getFirebaseuth();
        String idUsuario = Base64Custom.encode(auth.getCurrentUser().getEmail());
        String mesAno = DateCustom.mesDoAno(dataEscolhida);
        DatabaseReference ref = FirebaseConfig.getFirebase();
                 ref
                .child("movimentacao")
                .child(idUsuario)
                .child(mesAno)
                .push()
                .setValue(this);
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Movimentacao() {
    }

}
