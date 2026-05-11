
package carrerajuego;
import javax.swing.*;
import java.util.Random;
 
public class CorredorIntermedio extends Thread {
    
    private String nombre;
    private JLabel personaje;
    private JLabel lblResultado;
    public static boolean hayGanador = false; // public para que Enemigo y CrashJugador accedan
 
    public CorredorIntermedio(String nombre, JLabel personaje, JLabel lblResultado) {
        this.nombre = nombre;
        this.personaje = personaje;
        this.lblResultado = lblResultado;
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
                Thread.sleep(rand.nextInt(80));
 
                x += rand.nextInt(10) + 6;
 
                final int posX = x;
                SwingUtilities.invokeLater(() -> personaje.setLocation(posX, y));
 
                if (x >= 1150) {
                    synchronized (CorredorIntermedio.class) {
                        if (!hayGanador) {
                            hayGanador = true;
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
                                    Ventana2.reiniciarJuego();
                                    
                                } else {
                                    
                                    System.exit(0);
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
 