/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class VentanaMenu extends JFrame {

    private JLabel lblFondo;

    public VentanaMenu() {
        setSize(1250, 850);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);  // Layout absoluto
        // Fondo
        lblFondo = new JLabel(redimensionarImagen("fondoMenu.png", 1250, 850));
        lblFondo.setBounds(0, 0, 1250, 850);
        add(lblFondo);
        GestorAudio.getInstance().reproducir("/Users/mariosolis/NetBeansProjects/carreraJuego/src/carrerajuego/menuTheme.mp3");
        // Botón de inicio
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("start.png")); // jpg, png, gif
        Image imagenEscalada = iconoOriginal.getImage()
                .getScaledInstance(350, 200, Image.SCALE_SMOOTH); // ancho, alto
        ImageIcon iconoFinal = new ImageIcon(imagenEscalada);
        JButton iniciarJuego = new JButton(iconoFinal);
        iniciarJuego.setBounds(500, 425, 250, 70);
        iniciarJuego.setBorderPainted(false);   // sin borde
        iniciarJuego.setContentAreaFilled(false); // sin fondo gris
        iniciarJuego.setFocusPainted(false);    
        iniciarJuego.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblFondo.add(iniciarJuego);

        iniciarJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarNiveles();
            }
        });

    }

    private void mostrarNiveles() {
        Niveles ventana2 = new Niveles();
        ventana2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana2.setSize(1250, 850);
        ventana2.setResizable(false);
        ventana2.setLocationRelativeTo(null);
        ventana2.setVisible(true);
        dispose();
    }

    private ImageIcon redimensionarImagen(String ruta, int ancho, int alto) {
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
        Image imagenOriginal = iconoOriginal.getImage();
        Image imagenRedimensionada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }

}
