package com.example.labo4das;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class JuegoActivity extends AppCompatActivity {

    private JuegoNumeros juego; //clase donde esta el juego
    int intentos = 0;
    int contBoton = 10;
    private ArrayList<String> listaDeStrings;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    int num = 0;
    private int muertos = 0;
    private NotificationManager notificationManager;
    private final String CHANNEL_ID = "channelID"; // Un identificador único para la notificación
    private final String channelName = "channelName";
    private final int notificacion_id = 0;
    private int numAcertado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        //Crear canal de notificaciones
        createNotificationChannel();

        TextView textoNivel = findViewById(R.id.textNivel);
        //String nombreOpcion = getIntent().getStringExtra("nombreOpcion");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String valor = extras.getString("nombreOpcion");
            textoNivel.setText("Nivel: " + valor);
        }

        EditText editTextNum = findViewById(R.id.editNum);
        TextView textoIntento = findViewById(R.id.textIntentos);
        Button botonAdivinar = findViewById(R.id.botonAdivinar);
        textoIntento.setText("Te quedan  10 intentos");

        // Inicializa las variables
        listaDeStrings = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDeStrings);

        // Encuentra los elementos de la interfaz de usuario por sus IDs
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        juego = new JuegoNumeros();
        numAcertado = juego.generarNum();

        // OnClickListener al botón

        botonAdivinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Decrementar el contador de pulsaciones
                contBoton--;
                textoIntento.setText("Te quedan  " + contBoton + " intentos");

                String nuevoNumero = editTextNum.getText().toString();

                // Verifica si el EditText no está vacío
                if (!nuevoNumero.isEmpty()) {
                    juego.intentoUsuario(nuevoNumero);

                    muertos = juego.getAciertos();
                    int heridos = juego.getDigitosCorrectos();

                    // Introducir frase en el arraylist
                    listaDeStrings.add(nuevoNumero + ": " + muertos + " muertos" + " y " + heridos + " heridos");

                    // Notifica al adaptador que los datos han cambiado
                    adapter.notifyDataSetChanged();

                    // Se limpia el contenido del EditText después de agregar
                    editTextNum.getText().clear();
                }

                // Metodo para que el teclado desaparezca despues de introducir un numero
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextNum.getWindowToken(), 0);

                // Aparicion del resultado (numero aleatorio)
                Toast.makeText(getApplicationContext(), "EL NUMERO ES:" + String.valueOf(numAcertado), Toast.LENGTH_SHORT).show();

                // si acierta el numero se acaba el juego y lanza notificacion
                if (muertos == 4) {
                    Log.e("Adivinar", "Previo a la noti");
                    lanzarNotificacion();
                    Log.e("Adivinar", "Post noti");
                }

                // Verifica si se ha alcanzado la décima pulsación (1 final del juego)
                if (contBoton == 0) {
                    //Muestra dialogo
                    DialogFragment dialogo = new FinalDialogo();
                    dialogo.show(getSupportFragmentManager(), "etiqueta");
                }
            }
        });

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            int importancia = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importancia);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void lanzarNotificacion() {
        // Crear un Intent para abrir MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Esto asegura que MainActivity se inicie como una nueva tarea

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Crea una notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Número acertado")
                .setContentText("Vuelve a la pantalla principal")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.tarta, "OK", pendingIntent);

        NotificationManagerCompat notificacionman = NotificationManagerCompat.from(this);

        //MIRAR: (darle permisos a la app??)
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            requestPermissions(new String[] {android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificacionman.notify(notificacion_id, builder.build());

   }
}