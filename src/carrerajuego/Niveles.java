package carrerajuego;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author mariosolis
 */
public class Niveles extends JFrame {

    private JLabel lblFondo;

    public Niveles() {
        setSize(1250, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);  // Layout absoluto
        // Fondo
        lblFondo = new JLabel(redimensionarImagen("selector.png", 1250, 850));
        lblFondo.setBounds(0, 0, 1250, 850);
        add(lblFondo);
        GestorAudio.getInstance().reproducir("/Users/mariosolis/NetBeansProjects/carreraJuego/src/carrerajuego/menuTheme.mp3");
        // Botón de nivel1
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("level1.png")); // jpg, png, gif
        Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(350, 200, Image.SCALE_SMOOTH); // ancho, alto
        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
        JButton iniciarJuego = new JButton(iconoFinal);
        iniciarJuego.setBounds(100, 425, 250, 70);
        iniciarJuego.setBorderPainted(false);   // sin borde
        iniciarJuego.setContentAreaFilled(false); // sin fondo gris
        iniciarJuego.setFocusPainted(false);
        iniciarJuego.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblFondo.add(iniciarJuego);
         // Botón de nivel2
        ImageIcon iconoOriginal2 = new ImageIcon(getClass().getResource("level2.png")); // jpg, png, gif
        Image imagenEscalada2 = iconoOriginal2.getImage()
                .getScaledInstance(350, 200, Image.SCALE_SMOOTH); // ancho, alto
        ImageIcon iconoFinal2 = new ImageIcon(imagenEscalada2);
        JButton iniciarNivel2 = new JButton(iconoFinal2);
        iniciarNivel2.setBounds(270, 350, 250, 70);
        iniciarNivel2.setBorderPainted(false);   // sin borde
        iniciarNivel2.setContentAreaFilled(false); // sin fondo gris
        iniciarNivel2.setFocusPainted(false);
        iniciarNivel2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblFondo.add(iniciarNivel2);
         // Botón de nivel3
        ImageIcon iconoOriginal3 = new ImageIcon(getClass().getResource("level3.png")); // jpg, png, gif
        Image imagenEscalada3 = iconoOriginal3.getImage()
                .getScaledInstance(350, 200, Image.SCALE_SMOOTH); // ancho, alto
        ImageIcon iconoFinal3 = new ImageIcon(imagenEscalada3);
        JButton iniciarNivel3 = new JButton(iconoFinal3);
        iniciarNivel3.setBounds(730, 350, 250, 70);
        iniciarNivel3.setBorderPainted(false);   // sin borde
        iniciarNivel3.setContentAreaFilled(false); // sin fondo gris
        iniciarNivel3.setFocusPainted(false);
        iniciarNivel3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblFondo.add(iniciarNivel3);
         // Botón de nivel4
        ImageIcon iconoOriginal4 = new ImageIcon(getClass().getResource("level4.png")); // jpg, png, gif
        Image imagenEscalada4 = iconoOriginal4.getImage()
                .getScaledInstance(350, 200, Image.SCALE_SMOOTH); // ancho, alto
        ImageIcon iconoFinal4 = new ImageIcon(imagenEscalada4);
        JButton iniciarNivel4 = new JButton(iconoFinal4);
        iniciarNivel4.setBounds(950, 425, 250, 70);
        iniciarNivel4.setBorderPainted(false);   // sin borde
        iniciarNivel4.setContentAreaFilled(false); // sin fondo gris
        iniciarNivel4.setFocusPainted(false);
        iniciarNivel4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblFondo.add(iniciarNivel4);
         // Botón de nivel Final
        ImageIcon iconoOriginal5 = new ImageIcon(getClass().getResource("level5.png")); // jpg, png, gif
        Image imagenEscalada5 = iconoOriginal5.getImage()
                .getScaledInstance(350, 200, Image.SCALE_SMOOTH); // ancho, alto
        ImageIcon iconoFinal5 = new ImageIcon(imagenEscalada5);
        JButton iniciarNivel5 = new JButton(iconoFinal5);
        iniciarNivel5.setBounds(500, 250, 250, 70);
        iniciarNivel5.setBorderPainted(false);   // sin borde
        iniciarNivel5.setContentAreaFilled(false); // sin fondo gris
        iniciarNivel5.setFocusPainted(false);
        iniciarNivel5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblFondo.add(iniciarNivel5);
        iniciarJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nivelUno();
            }
        });
         iniciarNivel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nivelDos();
            }
        });
          iniciarNivel3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nivelTres();
            }
        });
           iniciarNivel4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nivelCuatro();
            }
        });
            iniciarNivel5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nivelCinco();
            }
        });
    }

    private ImageIcon redimensionarImagen(String ruta, int ancho, int alto) {
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
        Image imagenOriginal = iconoOriginal.getImage();
        Image imagenRedimensionada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

    private void nivelUno() {
        Ventana ventana2 = new Ventana();
        ventana2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana2.setSize(1250, 850);
        ventana2.setResizable(false);
        ventana2.setLocationRelativeTo(null);
        ventana2.setVisible(true);
        dispose();
    }
    
    private void nivelDos() {
        Ventana2 ventana3 = new Ventana2();
        ventana3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana3.setSize(1250, 850);
        ventana3.setResizable(false);
        ventana3.setLocationRelativeTo(null);
        ventana3.setVisible(true);
        dispose();
    }
    
    private void nivelTres() {
        Ventana3 ventana4 = new Ventana3();
        ventana4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana4.setSize(1250, 850);
        ventana4.setResizable(false);
        ventana4.setLocationRelativeTo(null);
        ventana4.setVisible(true);
        dispose();
    }
    
    private void nivelCuatro() {
        Ventana4 ventana5 = new Ventana4();
        ventana5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana5.setSize(1250, 850);
        ventana5.setResizable(false);
        ventana5.setLocationRelativeTo(null);
        ventana5.setVisible(true);
        dispose();
    }
    private void nivelCinco() {
        Ventana5 ventana6 = new Ventana5();
        ventana6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana6.setSize(1250, 850);
        ventana6.setResizable(false);
        ventana6.setLocationRelativeTo(null);
        ventana6.setVisible(true);
        dispose();
    }
}
