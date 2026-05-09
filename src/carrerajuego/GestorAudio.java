
package carrerajuego;

import java.io.FileInputStream;
import javazoom.jl.player.Player;

/**
 *
 * @author mariosolis
 */
public class GestorAudio {
     private static GestorAudio instancia;
    private Thread hiloAudio;
    private Player playerActual;          // ← guardar referencia al player
    private boolean reproduciendo = false;
    private String rutaActual;

    private GestorAudio() {}

    public static GestorAudio getInstance() {
        if (instancia == null) {
            instancia = new GestorAudio();
        }
        return instancia;
    }

    public void reproducir(String ruta) {
        if (ruta.equals(rutaActual) && reproduciendo) return;
        cambiar(ruta);
    }

    public void cambiar(String ruta) {
        detener(); // detiene y cierra el player anterior

        rutaActual = ruta;
        reproduciendo = true;

        hiloAudio = new Thread(() -> {
            while (reproduciendo) {
                try {
                    FileInputStream fis = new FileInputStream(rutaActual);
                    
                    synchronized (this) {
                        playerActual = new Player(fis); // ← guardar antes de play()
                    }
                    
                    playerActual.play(); // bloquea hasta que termina la canción

                } catch (javazoom.jl.decoder.JavaLayerException e) {
                    // Si se cerró el player manualmente, salir del bucle
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            reproduciendo = false;
        });

        hiloAudio.setDaemon(true);
        hiloAudio.start();
    }

    public synchronized void detener() {
        reproduciendo = false;

        // Cerrar el player explícitamente ← esto es lo que faltaba
        if (playerActual != null) {
            playerActual.close();
            playerActual = null;
        }

        if (hiloAudio != null) {
            hiloAudio.interrupt();
            hiloAudio = null;
        }
    }

    public boolean estaReproduciendo() {
        return reproduciendo;
    }

    public String getRutaActual() {
        return rutaActual;
    }
}
