package com.example.labo4das;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
        aciertos = 0;
        digitosCorrectos = 0;
        String numeroAleatorioStr = String.valueOf(this.getNumeroAleatorio());

        Set<Character> digitosConsiderados = new HashSet<>();

        for (int i = 0; i < numeroUsuario.length(); i++) {
            char digitoAleatorio = numeroAleatorioStr.charAt(i);
            char digitoUsuario = numeroUsuario.charAt(i);

            if (digitoAleatorio == digitoUsuario) {
                aciertos++; // Acierto de dígito (muerto)
            } else if (numeroAleatorioStr.contains(String.valueOf(digitoUsuario)) && !digitosConsiderados.contains(digitoUsuario)) {
                digitosCorrectos++; // Dígito correcto (independientemente de la posición) (herido)
                digitosConsiderados.add(digitoUsuario);
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

