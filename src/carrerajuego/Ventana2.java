/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrerajuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Ventana2 extends JFrame {

    private JButton btnIniciar;
    public static JLabel[] personajes;
    public static JLabel lblResultado;
    private JLabel lblMeta;
    private JLabel lblFondo;

    public static int puntosTotales = 0;
    private static JLabel lblPuntos;

    // Obstáculos y enemigo
    private static List<Obstaculo> obstaculos = new ArrayList<>();
    private static JLabel lblEnemigo;

    // Hilos controlados
    private static CrashJugador crashJugador;
    public static Enemigo enemigoHilo;
    public static Thread hiloUkauka;
    public static Thread hiloVortex;

    public Ventana2() {
        setTitle("Crash Bandicoot Scape of Cortex");
        setSize(1250, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        GestorAudio.getInstance().reproducir("/Users/mariosolis/NetBeansProjects/carreraJuego/src/carrerajuego/levelTheme.mp3");

        // Fondo
        lblFondo = new JLabel(redimensionarImagen("fondo3.png", 1250, 850));
        lblFondo.setBounds(0, 0, 1250, 850);
        add(lblFondo);

        // Personajes
        personajes = new JLabel[3];
        personajes[0] = new JLabel(redimensionarImagen("ukauka.png", 100, 100));
        personajes[1] = new JLabel(redimensionarImagen("crash.png", 100, 100));
        personajes[2] = new JLabel(redimensionarImagen("vortex.png", 100, 100));
        personajes[0].setBounds(50, 100, 100, 100);  // ukauka
        personajes[1].setBounds(50, 400, 100, 100);  // crash
        personajes[2].setBounds(50, 690, 100, 100);  // vortex
        for (JLabel p : personajes) {
            lblFondo.add(p);
        }

        // Enemigo
        lblEnemigo = new JLabel(redimensionarImagen("enemy.png", 80, 80));
        lblEnemigo.setBounds(1100, 415, 80, 80);
        lblFondo.add(lblEnemigo);
        //Obstaculos
        obstaculos.clear();
        agregarObstaculo("tnt", 200, 420);
        agregarObstaculo("caja", 450, 420);
        agregarObstaculo("sorpresa", 700, 420);
        agregarObstaculo("tnt", 200, 440);
        // Meta
        lblMeta = new JLabel(redimensionarImagen("cristal.png", 100, 100));
        lblMeta.setBounds(1150, 360, 100, 100);
        lblFondo.add(lblMeta);

        // Resultado
        lblResultado = new JLabel("¿Quién ganará?");
        lblResultado.setForeground(Color.WHITE);
        lblResultado.setBounds(220, 20, 400, 30);
        lblFondo.add(lblResultado);

        //puntos
        lblPuntos = new JLabel("Puntos: " + puntosTotales);
        lblPuntos.setForeground(Color.YELLOW); // Amarillo para que resalte
        lblPuntos.setFont(new Font("Arial", Font.BOLD, 18)); // Un poco de estilo
        lblPuntos.setBounds(1000, 20, 200, 30); // Posicionado a la derecha
        lblFondo.add(lblPuntos);

        // Botón iniciar
        btnIniciar = new JButton("Iniciar Carrera");
        btnIniciar.setBounds(50, 20, 150, 30);
        btnIniciar.addActionListener(e -> iniciarCarrera());
        lblFondo.add(btnIniciar);

        // Teclado para Crash
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (crashJugador == null) {
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    crashJugador.presionarDerecha();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    crashJugador.presionarIzquierda();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    crashJugador.saltar();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (crashJugador == null) {
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    crashJugador.soltarDerecha();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    crashJugador.soltarIzquierda();
                }

            }

        });

        setFocusable(true);
        setVisible(true);
        

    }

    

    
    public static void mostrarNiveles() {
        GestorAudio.getInstance().detener();
        Niveles ventanaN = new Niveles();
        ventanaN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaN.setSize(1250, 850);
        ventanaN.setResizable(false);
        ventanaN.setLocationRelativeTo(null);
        ventanaN.setVisible(true);
    }

    private void agregarObstaculo(String tipo, int x, int y) {
        // Verificar si existe la imagen
        java.net.URL url = getClass().getResource(tipo + ".png");
        if (url == null) {
            System.out.println("ERROR: no se encontró " + tipo + ".png");
            return; // evita el crash
        }
        JLabel lbl = new JLabel(redimensionarImagen(tipo + ".png", 80, 80));
        lbl.setBounds(x, y, 80, 80);
        lblFondo.add(lbl);
        obstaculos.add(new Obstaculo(tipo, lbl));
    }

    private void iniciarCarrera() {
        lblResultado.setText("Carrera en progreso...");
        Corredor.resetearGanador();

        crashJugador = new CrashJugador(personajes[1], lblResultado, obstaculos,this);
        crashJugador.start();

        hiloUkauka = new Corredor("UkaUka", personajes[0], lblResultado,this);
        hiloVortex = new Corredor("Vortex", personajes[2], lblResultado,this);
        hiloUkauka.start();
        hiloVortex.start();

        enemigoHilo = new Enemigo(lblEnemigo, personajes[1], lblResultado,this);
        enemigoHilo.start();

        requestFocusInWindow();
    }

    public static void reiniciarJuego() {
        // Detener TODOS los hilos activos
        if (crashJugador != null) {
            crashJugador.detener();
        }
        if (enemigoHilo != null) {
            enemigoHilo.detener();
        }
        if (hiloUkauka != null) {
            hiloUkauka.interrupt();
        }
        if (hiloVortex != null) {
            hiloVortex.interrupt();
        }

        Corredor.resetearGanador();

        SwingUtilities.invokeLater(() -> {
            reiniciarUbi();
            lblResultado.setText("¿Quién ganará?");
            lblPuntos.setText("Puntos: " + puntosTotales);
            // Crear hilos nuevos
            crashJugador = new CrashJugador(personajes[1], lblResultado, obstaculos,null);

            hiloUkauka = new Corredor("UkaUka", personajes[0], lblResultado,null);
            hiloVortex = new Corredor("Vortex", personajes[2], lblResultado,null);

            enemigoHilo = new Enemigo(lblEnemigo, personajes[1], lblResultado,null);
        });
    }

    public static void reiniciarUbi() {
        SwingUtilities.invokeLater(() -> {
            personajes[0].setLocation(50, 100);
            personajes[1].setLocation(50, 400);
            personajes[2].setLocation(50, 690);
            if (lblEnemigo != null) {
                lblEnemigo.setLocation(1100, 415);
            }
        });
    }

    private ImageIcon redimensionarImagen(String ruta, int ancho, int alto) {
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
        Image imagenOriginal = iconoOriginal.getImage();
        Image imagenRedimensionada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    public static void actualizarPuntuacion(boolean gano) {
        if (gano) {
            puntosTotales += 100;
        } else {
            puntosTotales -= 25;
            if (puntosTotales < 0) {
                puntosTotales = 0;
            }
        }

        // Actualización visual obligatoria
        if (lblPuntos != null) {
            lblPuntos.setText("Puntos: " + puntosTotales);
            lblPuntos.repaint(); // <--- Fuerza a Java a pintar el cambio
            lblPuntos.revalidate(); // <--- Reorganiza el layout si es necesario
        }
    }
}
