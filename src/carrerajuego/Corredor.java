package carrerajuego;

/**
 *
 * @author mariosolis
 */
import javax.swing.*;
import java.util.Random;

public class Corredor extends Thread {

    private String nombre;

    private JLabel personaje;
    private JLabel lblResultado;
    private JFrame ventanaActual;
    public static boolean hayGanador = false;
    public static String nombreVentana;
    public String nivel1 = "carrerajuego.Ventana[";
    public String nivel2 = "carrerajuego.Ventana2";
    public String nivel3 = "carrerajuego.Ventana3";
    public String nivel4 = "carrerajuego.Ventana4";
    public String nivel5 = "carrerajuego.Ventana5";

    public Corredor(String nombre, JLabel personaje, JLabel lblResultado, JFrame ventanaActual) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.lblResultado = lblResultado;
        this.ventanaActual = ventanaActual;
    }

    public static String detectarVentana(JFrame ventanaActual) {
        nombreVentana = ventanaActual.toString();
        nombreVentana = nombreVentana.substring(0, 21);
        return nombreVentana;

    }

    public static void resetearGanador() {
        hayGanador = false;
    }

    @Override
    public void run() {
        Random rand = new Random();
        int x = personaje.getX();
        int y = personaje.getY();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(rand.nextInt(100));

                x += rand.nextInt(10) + 1;

                final int posX = x;
                SwingUtilities.invokeLater(() -> personaje.setLocation(posX, y));

                if (x >= 1150) {
                    synchronized (Corredor.class) {
                        if (!hayGanador) {
                            hayGanador = true;
                            if (this.nombre.equalsIgnoreCase("Crash") || this.nombre.equalsIgnoreCase("Jugador")) {
                                Puntos.getPuntos();
                            } else {
                                Puntos.getPuntos();
                            }

                            final String ganador = nombre;
                            SwingUtilities.invokeLater(() -> {
                                lblResultado.setText("¡" + ganador + " ganó la carrera!");
                                String[] opciones = {"Reintentar", "Salir"};
                                int respuesta = JOptionPane.showOptionDialog(
                                        null,
                                        "¡" + ganador + " ganó!\n¿Qué deseas hacer?",
                                        "Fin de carrera",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.PLAIN_MESSAGE,
                                        null, opciones, opciones[0]
                                );
                                if (respuesta == 0) {
                                    if (detectarVentana(ventanaActual).equals(nivel1)) {
                                        Ventana.reiniciarJuego();
                                    }
                                    if (detectarVentana(ventanaActual).equals(nivel2)) {
                                        Ventana2.reiniciarJuego();
                                    }
                                } else {
                                    if (detectarVentana(ventanaActual).equals(nivel1)) {
                                        Ventana.mostrarNiveles();
                                        ventanaActual.dispose();
                                    }

                                    if (detectarVentana(ventanaActual).equals(nivel2)) {
                                        Ventana2.mostrarNiveles();
                                        ventanaActual.dispose();
                                    }
                                }
                            });
                        }
                    }
                    break;
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
