package com.example.labo4das;
import java.util.Random;
public class JuegoNumeros {
    private int numeroAleatorio;
    private int digitosCorrectos = 0;
    private int aciertos = 0;

    public int getNumeroAleatorio(){
        return numeroAleatorio;
    }

    public int generarNum(){
        Random generador = new Random();
        numeroAleatorio = generador.nextInt(9000) + 1000;
        return numeroAleatorio;
    }

    public void intentoUsuario(String numeroUsuario) {
        aciertos=0;
        digitosCorrectos=0;
        String numeroAleatorioStr = String.valueOf(this.getNumeroAleatorio());
        int longitud = Math.min(numeroAleatorioStr.length(), numeroUsuario.length());
        // Verifica cada dígito y su posición
        for (int i = 0; i < longitud; i++) {
            char digitoAleatorio = numeroAleatorioStr.charAt(i);
            char digitoUsuario = numeroUsuario.charAt(i);

            if (digitoAleatorio == digitoUsuario) {
                aciertos++; // Acierto de dígito (muerto)
            }

            if (numeroAleatorioStr.contains(String.valueOf(digitoUsuario))) {
                digitosCorrectos++; // Dígito correcto (independientemente de la posición) (herido)
            }
        }
    }

    public int getDigitosCorrectos() {
        return digitosCorrectos;
    }
    public int getAciertos() {
        return aciertos;
    }

}

