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
    public static int puntosTotales = 0;
    private JLabel lblFondo;

    public VentanaMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 850);
        setResizable(false);
        setLocationRelativeTo(null);
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
        iniciarJuego.setBounds(450, 390, 350, 200);
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
        
        ImageIcon iconoOriginalCreditos = new ImageIcon(getClass().getResource("creditos.png")); 
        Image imagenEscaladaCreditos = iconoOriginalCreditos.getImage()
                .getScaledInstance(350, 150, Image.SCALE_SMOOTH); // Mismo tamaño para mantener simetría
        ImageIcon iconoFinalCreditos = new ImageIcon(imagenEscaladaCreditos);
        JButton botonCreditos = new JButton(iconoFinalCreditos);
        botonCreditos.setBounds(850, 520, 350, 200); 
        
        botonCreditos.setBorderPainted(false);   
        botonCreditos.setContentAreaFilled(false); 
        botonCreditos.setFocusPainted(false);    
        botonCreditos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblFondo.add(botonCreditos);

        botonCreditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                mostrarCreditos(); 
            }
        });

    }
    
     private void mostrarCreditos() {
        ventanaCreditos vc = new ventanaCreditos();
        vc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vc.setSize(1250, 850); // Mismo tamaño de tu menú
        vc.setResizable(false);
        vc.setLocationRelativeTo(null); // Centrar en pantalla
        vc.setVisible(true);
        dispose(); // Cierra el menú actual
        System.out.println("Abriendo créditos...");
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
