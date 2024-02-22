package com.example.labo4das;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DificultadDialogo extends DialogFragment {
    private String opcionSeleccionada;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Selecciona un nivel");
        CharSequence[] opciones = {"Facil", "Normal", "Dificil"};

        builder.setItems(opciones, new Listener()); // idx: 0, 1 y 2 respectivamente

        builder.setNeutralButton("Ok", new Listener()); // idx: -3

        return builder.create();
    }

    private class Listener implements DialogInterface.OnClickListener { //Crear Listener

        @Override
        public void onClick(DialogInterface dialog, int which) {
            opcionSeleccionada = "";
            switch (which) {
                // TODO: Llamar a que empiece el juego para case 0, 1 y 2
                case 0:
                    opcionSeleccionada = "Facil";
                    break;
                case 1:
                    opcionSeleccionada = "Normal";
                    break;
                case 2:
                    opcionSeleccionada = "Dificil";
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    // NOP
                    break;
            }

            // Intent desde MainAsctivity a JuegoActivity
            Intent intent = new Intent(getContext(), JuegoActivity.class);
            //Añadir al intent informacion como el nombre de la opcion seleccionada
            intent.putExtra("nombreOpcion", opcionSeleccionada);
            // Inicia la actividad
            getContext().startActivity(intent);
            dismiss();

        }
    }
}
