/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrerajuego;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author mariosolis
 */
public class Puntos {

    public static JLabel lblPuntos;

    private static int puntos = 0;

    public static void crearPuntuacion() {
        lblPuntos = new JLabel("Puntos: " + Puntos.getPuntos());
        lblPuntos.setForeground(Color.YELLOW); // Amarillo para que resalte
        lblPuntos.setFont(new Font("Arial", Font.BOLD, 18)); // Un poco de estilo
        lblPuntos.setBounds(1000, 20, 200, 30); // Posicionado a la derecha
    }

    public static void incrementarPuntos(int cantidad) {
        puntos += cantidad;
    }

    public static void drecrementarPuntos(int cantidad) {
        puntos -= cantidad;
        if (puntos < 0) {
            puntos = 0;
        }
    }

    public static void reiniciarPuntos() {
        puntos = 0;
    }

    public static int getPuntos() {
        return puntos;
    }

    public static void actualizarPuntuacion(boolean gano) {
        if (gano) {
            Puntos.incrementarPuntos(100);

        } else {
            Puntos.drecrementarPuntos(25);
            if (Puntos.getPuntos() < 0) {
                Puntos.reiniciarPuntos();
            }
        }

        // Actualización visual obligatoria
        if (lblPuntos != null) {
            lblPuntos.setText("Puntos: " + Puntos.getPuntos());
            lblPuntos.repaint(); // <--- Fuerza a Java a pintar el cambio
            lblPuntos.revalidate(); // <--- Reorganiza el layout si es necesario
        }
    }

}
