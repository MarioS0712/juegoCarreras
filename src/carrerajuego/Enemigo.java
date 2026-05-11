/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrerajuego;

import javax.swing.*;
import java.util.Random;

public class Enemigo extends Thread {

    private JLabel label, label2, label3;
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

    public Enemigo(JLabel label, JLabel label2, JLabel label3, JLabel crash, JLabel lblResultado, JFrame ventanaActual) {
        this.label = label;
        this.label2 = label2;
        this.label3 = label3;
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
        // Solo inicializar label2/label3 si existen
        int x2 = (label2 != null) ? label2.getX() : 0;
        int y2 = (label2 != null) ? label2.getY() : 0;
        int x3 = (label3 != null) ? label3.getX() : 0;
        int y3 = (label3 != null) ? label3.getY() : 0;
        while (activo && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(30);

                // Se mueve hacia la izquierda
                if (Corredor.detectarVentana(ventanaActual).equals(nivel1)) {
                    x -= rand.nextInt(5) + 2;
                }
                if (Corredor.detectarVentana(ventanaActual).equals(nivel2)) {
                    x -= rand.nextInt(15) + 2;
                }
                if (Corredor.detectarVentana(ventanaActual).equals(nivel3)) {
                    x -= rand.nextInt(20) + 2;
                }
                if (Corredor.detectarVentana(ventanaActual).equals(nivel4)) {
                    x -= rand.nextInt(25) + 2;
                }
                if (Corredor.detectarVentana(ventanaActual).equals(nivel5)) {
                    x -= rand.nextInt(35) + 2;
                }

                // Reaparece por la derecha al salir por la izquierda
                if (x < -100) {
                    x = 1250;
                }
                final int posX = x;
                SwingUtilities.invokeLater(() -> label.setLocation(posX, y));
                //Nivel5 Reaparecen por abajo
                if (Corredor.detectarVentana(ventanaActual).equals(nivel5) && label2 != null && label3 != null) {
                    y2 -= rand.nextInt(8) + 3; // sube
                    y3 -= rand.nextInt(8) + 3; // sube

                    // Reaparecen por abajo al salir por arriba
                    if (y2 < -100) {
                        y2 = 750;
                    }
                    if (y3 < -100) {
                        y3 = 750;
                    }

                    final int posY2 = y2;
                    final int posX2 = x2;
                    final int posY3 = y3;
                    final int posX3 = x3;
                    SwingUtilities.invokeLater(() -> label2.setLocation(posX2, posY2));
                    SwingUtilities.invokeLater(() -> label3.setLocation(posX3, posY3));

                    // Colisión label2 y label3 con Crash
                    if (label2.getBounds().intersects(crash.getBounds())
                            || label3.getBounds().intersects(crash.getBounds())) {
                        activo = false;
                        SwingUtilities.invokeLater(() -> mostrarDerrota("¡Un enemigo atrapó a Crash!"));
                        Puntos.drecrementarPuntos(10);
                        break;
                    }
                }
                // Colisión con Crash
                if (label.getBounds().intersects(crash.getBounds())) {
                    activo = false;
                    y2 = 750;
                    y3 = 750;
                    final int posY2reset = y2;
                    final int posY3reset = y3;
                    SwingUtilities.invokeLater(() -> {
                        label2.setLocation(x2, posY2reset);
                        label3.setLocation(x3, posY3reset);
                    });
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
            // Detener rivales y enemigo Ventana1
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
            //Ventana2
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
                CrashJugador.interrupted();
            }
            //Ventana3
            if (Corredor.detectarVentana(ventanaActual).equals(nivel3)) {
                if (Ventana3.hiloUkauka != null) {
                    Ventana3.hiloUkauka.interrupt();
                }
                if (Ventana3.hiloVortex != null) {
                    Ventana3.hiloVortex.interrupt();
                }
                if (Ventana3.enemigoHilo != null) {
                    Ventana3.enemigoHilo.detener();
                }
                CrashJugador.interrupted();
            }
            //Ventana4
            if (Corredor.detectarVentana(ventanaActual).equals(nivel4)) {
                if (Ventana4.hiloUkauka != null) {
                    Ventana4.hiloUkauka.interrupt();
                }
                if (Ventana4.hiloVortex != null) {
                    Ventana4.hiloVortex.interrupt();
                }
                if (Ventana4.enemigoHilo != null) {
                    Ventana4.enemigoHilo.detener();
                }
                CrashJugador.interrupted();
            }
            //Ventana5
            if (Corredor.detectarVentana(ventanaActual).equals(nivel5)) {
                if (Ventana5.hiloUkauka != null) {
                    Ventana5.hiloUkauka.interrupt();
                }
                if (Ventana5.hiloVortex != null) {
                    Ventana5.hiloVortex.interrupt();
                }
                if (Ventana5.enemigoHilo != null) {
                    Ventana5.enemigoHilo.detener();
                }
                CrashJugador.interrupted();
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
                if (Corredor.detectarVentana(ventanaActual).equals(nivel3)) {
                    Ventana3.reiniciarJuego();
                }
                if (Corredor.detectarVentana(ventanaActual).equals(nivel4)) {
                    Ventana4.reiniciarJuego();
                }
                if (Corredor.detectarVentana(ventanaActual).equals(nivel5)) {
                    Ventana5.reiniciarJuego();
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
                if (Corredor.detectarVentana(ventanaActual).equals(nivel3)) {
                    Ventana3.mostrarNiveles();
                    ventanaActual.dispose();
                }

                if (Corredor.detectarVentana(ventanaActual).equals(nivel4)) {
                    Ventana4.mostrarNiveles();
                    ventanaActual.dispose();
                }

                if (Corredor.detectarVentana(ventanaActual).equals(nivel5)) {
                    Ventana5.mostrarNiveles();
                    ventanaActual.dispose();
                }
            }
        }
    }
}
