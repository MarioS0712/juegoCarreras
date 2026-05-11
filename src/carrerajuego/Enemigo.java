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
    private JFrame ventanaActual;
    private boolean activo = true;
    public String nivel1 = "carrerajuego.Ventana[";
    public String nivel2 = "carrerajuego.Ventana2";
    public String nivel3 = "carrerajuego.Ventana3";
    public String nivel4 = "carrerajuego.Ventana4";
    public String nivel5 = "carrerajuego.Ventana5";

    public Enemigo(JLabel label, JLabel crash, JLabel lblResultado, JFrame ventanaActual) {
        this.label = label;
        this.crash = crash;
        this.lblResultado = lblResultado;
        this.ventanaActual = ventanaActual;
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
                    Puntos.drecrementarPuntos(10);
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
            lblResultado.setText(mensaje);
            // Detener rivales y enemigo
            if (Corredor.detectarVentana(ventanaActual).equals(nivel1)) {
                if (Ventana.hiloUkauka != null) {
                    Ventana.hiloUkauka.interrupt();
                }
                if (Ventana.hiloVortex != null) {
                    Ventana.hiloVortex.interrupt();
                }
                if (Ventana.enemigoHilo != null) {
                    Ventana.enemigoHilo.detener();
                }
                CrashJugador.interrupted();
            }
            if (Corredor.detectarVentana(ventanaActual).equals(nivel2)) {
                if (Ventana2.hiloUkauka != null) {
                    Ventana2.hiloUkauka.interrupt();
                }
                if (Ventana2.hiloVortex != null) {
                    Ventana2.hiloVortex.interrupt();
                }
                if (Ventana2.enemigoHilo != null) {
                    Ventana2.enemigoHilo.detener();
                }
            }

            String[] opciones = {"Reintentar", "Salir"};
            int respuesta = JOptionPane.showOptionDialog(
                    null, mensaje, "¡Perdiste!",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]
            );
            if (respuesta == 0) {
                System.out.println("Nombre Ventana: \n" + Corredor.detectarVentana(ventanaActual));
                if (Corredor.detectarVentana(ventanaActual).equals(nivel1)) {
                    Ventana.reiniciarJuego();
                }
                if (Corredor.detectarVentana(ventanaActual).equals(nivel2)) {
                    Ventana2.reiniciarJuego();
                }
            } else {
                if (Corredor.detectarVentana(ventanaActual).equals(nivel1)) {
                    Ventana.mostrarNiveles();
                    ventanaActual.dispose();
                }

                if (Corredor.detectarVentana(ventanaActual).equals(nivel2)) {
                    Ventana2.mostrarNiveles();
                    ventanaActual.dispose();
                }
            }
        }
    }
}
