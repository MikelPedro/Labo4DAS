package com.example.labo4das;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        // Para saber si vienes de la notificacion
        Bundle extras=getIntent().getExtras();
        if (extras!=null){
            int id=extras.getInt("id");
            NotificationManager elManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            elManager.cancel(id);
        }
    }
    //Dialogo de instrucciones: (Enlazar metodo con el boton correspondiente)
    public void OnClick(View v){
        DialogFragment dialogo = new ClaseDialogo();
        dialogo.show(getSupportFragmentManager(), "etiqueta");
    }

    public void OnClickDificultad(View v){
        DialogFragment dialogo = new DificultadDialogo();
        dialogo.show(getSupportFragmentManager(), "etiqueta");
    }

}