/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrerajuego;

import javax.swing.*;
import java.util.Random;

public class Enemigo extends Thread {

    private JLabel label;
    private JLabel crash;
    private JLabel lblResultado;
    private boolean activo = true;

    public Enemigo(JLabel label, JLabel crash, JLabel lblResultado) {
        this.label = label;
        this.crash = crash;
        this.lblResultado = lblResultado;
    }

    public void detener() {
        activo = false;
        interrupt();
    }

    @Override
    public void run() {
        Random rand = new Random();
        int x = label.getX();
        int y = label.getY();

        while (activo && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(30);

                // Se mueve hacia la izquierda
                x -= rand.nextInt(5) + 2;

                // Reaparece por la derecha al salir por la izquierda
                if (x < -100) {
                    x = 1250;
                }

                final int posX = x;
                SwingUtilities.invokeLater(() -> label.setLocation(posX, y));

                // Colisión con Crash
                if (label.getBounds().intersects(crash.getBounds())) {
                    activo = false;
                    SwingUtilities.invokeLater(()
                            -> mostrarDerrota("¡Un enemigo atrapó a Crash!")
                    );
                    break;
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void mostrarDerrota(String mensaje) {
        if (!Corredor.hayGanador) {
            Corredor.hayGanador = true;
            Ventana.lblResultado.setText(mensaje);
            // Detener rivales y enemigo
            if (Ventana.hiloUkauka != null) {
                Ventana.hiloUkauka.interrupt();
            }
            if (Ventana.hiloVortex != null) {
                Ventana.hiloVortex.interrupt();
            }
            if (Ventana.enemigoHilo != null) {
                Ventana.enemigoHilo.detener();
            }
            String[] opciones = {"Reintentar", "Salir"};
            int respuesta = JOptionPane.showOptionDialog(
                    null, mensaje, "¡Perdiste!",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]
            );
            if (respuesta == 0) {
                Ventana.reiniciarJuego();
            } else {
                System.exit(0);
            }
        }
    }
}
