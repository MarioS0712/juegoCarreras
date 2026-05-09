package carrerajuego;

import javax.swing.*;
import java.util.List;

public class CrashJugador extends Thread {

    private JLabel label;
    private JLabel lblResultado;
    private List<Obstaculo> obstaculos;
    private boolean activo = true;

    private boolean moverDerecha = false;
    private boolean moverIzquierda = false;
    private boolean saltando = false;
    private int velY = 0;
    private int yInicial;

    public CrashJugador(JLabel label, JLabel lblResultado, List<Obstaculo> obstaculos) {
        this.label = label;
        this.lblResultado = lblResultado;
        this.obstaculos = obstaculos;
        this.yInicial = label.getY();
    }

    // Llamados desde el KeyListener de Ventana
    public void presionarDerecha() {
        moverDerecha = true;
    }

    public void soltarDerecha() {
        moverDerecha = false;
    }

    public void presionarIzquierda() {
        moverIzquierda = true;
    }

    public void soltarIzquierda() {
        moverIzquierda = false;
    }

    public void saltar() {
        if (!saltando) {
            saltando = true;
            velY = -18;
        }
    }

    public void detener() {
        activo = false;
        interrupt();
    }

    @Override
    public void run() {
        int x = label.getX();
        int y = label.getY();

        while (activo && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(16); // ~60 FPS

                // Movimiento horizontal
                if (moverDerecha) {
                    x += 8;
                }
                if (moverIzquierda) {
                    x -= 8;
                }

                // Salto con gravedad
                if (saltando) {
                    velY += 1;
                    y += velY;
                    if (y >= yInicial) {
                        y = yInicial;
                        velY = 0;
                        saltando = false;
                    }
                }

                // Límite derecho
                if (x > 1150) {
                    x = 1150;
                }

                final int posX = x;
                final int posY = y;
                SwingUtilities.invokeLater(() -> label.setLocation(posX, posY));

                // Colisión con obstáculos
                for (Obstaculo obs : obstaculos) {
                    if (label.getBounds().intersects(obs.getBounds())) {
                        // Solo pierde si NO está saltando por encima
                        if (!saltando || velY >= 0) {
                            activo = false;
                            final String tipo = obs.tipo;
                            SwingUtilities.invokeLater(()
                                    -> mostrarDerrota("¡Crash chocó con una caja " + tipo + "!")
                            );
                            return;
                        }
                    }
                }

                // Llegó a la meta
                if (x >= 1150) {
                    synchronized (Corredor.class) {
                        if (!Corredor.hayGanador) {
                            Corredor.hayGanador = true;
                            SwingUtilities.invokeLater(()
                                    -> mostrarVictoria("¡Crash ganó la carrera!")
                            );
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

    private void mostrarVictoria(String mensaje) {
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

        lblResultado.setText(mensaje);
        String[] opciones = {"Reintentar", "Salir"};
        int respuesta = JOptionPane.showOptionDialog(
                null, mensaje, "¡Ganaste!",
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

    private void mostrarDerrota(String mensaje) {
        if (!Corredor.hayGanador) {
            Corredor.hayGanador = true;
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
            lblResultado.setText(mensaje);
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
