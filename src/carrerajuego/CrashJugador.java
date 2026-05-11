package carrerajuego;

import javax.swing.*;
import java.util.List;

public class CrashJugador extends Thread {

    private JLabel label;
    private JLabel lblResultado;
    private List<Obstaculo> obstaculos;
    private boolean activo = true;
    private JFrame ventanaActual;
    private boolean moverDerecha = false;
    private boolean moverIzquierda = false;
    private boolean saltando = false;
    private int velY = 0;
    private int yInicial;
    public String nivel1 = "carrerajuego.Ventana[";
    public String nivel2 = "carrerajuego.Ventana2";
    public String nivel3 = "carrerajuego.Ventana3";
    public String nivel4 = "carrerajuego.Ventana4";
    public String nivel5 = "carrerajuego.Ventana5";

    public CrashJugador(JLabel label, JLabel lblResultado, List<Obstaculo> obstaculos, JFrame ventanaActual) {
        this.label = label;
        this.lblResultado = lblResultado;
        this.obstaculos = obstaculos;
        this.yInicial = label.getY();
        this.ventanaActual = ventanaActual;
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
                            Puntos.drecrementarPuntos(10);
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
                            Puntos.incrementarPuntos(100);
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

        lblResultado.setText(mensaje);
        String[] opciones = {"Reintentar", "Salir"};
        int respuesta = JOptionPane.showOptionDialog(
                null, mensaje, "¡Ganaste!",
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

    private void mostrarDerrota(String mensaje) {

        if (!Corredor.hayGanador) {
            Corredor.hayGanador = true;
            // Detener rivales y enemigo nivel 1
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
            lblResultado.setText(mensaje);
            String[] opciones = {"Reintentar", "Salir"};
            int respuesta = JOptionPane.showOptionDialog(
                    null, mensaje, "¡Perdiste!",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]
            );
            if (respuesta == 0) {
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
