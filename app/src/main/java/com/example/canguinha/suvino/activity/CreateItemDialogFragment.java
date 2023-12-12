package com.example.canguinha.suvino.activity;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CreateItemDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Criar Receita/Despesa")
                // Adicione os campos necessários para criar a receita/despesa, como EditTexts, Spinners, etc.
                // Substitua o código abaixo pelo layout e pela lógica necessários para criar a receita/despesa
                .setMessage("Adicione os detalhes aqui")
                .setPositiveButton("Salvar", (dialog, which) -> {
                    // Lógica para salvar a receita/despesa
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    // Lógica para cancelar a criação da receita/despesa
                });

        return builder.create();
    }
}
