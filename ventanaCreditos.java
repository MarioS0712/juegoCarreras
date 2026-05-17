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

/**
 *
 * @author joaqu
 */
public class ventanaCreditos extends JFrame{
    private JLabel lblFondo;
    private JLabel lblImagenCreditos;

    public ventanaCreditos() {
        // Configuraciones básicas idénticas al menú para mantener el tamaño
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250, 850);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null); 

        lblFondo = new JLabel(redimensionarImagen("fondoCreditos.jpg", 1250, 850));
        lblFondo.setBounds(0, 0, 1250, 850);
        add(lblFondo);

       
        int posX   = 200;  // Izquierda o Derecha (Menor número = más a la izquierda)
        int posY   = 20;  // Altura: Arriba o Abajo (Menor número = más arriba)
        int ancho  = 800;  // Qué tan ancha quieres la imagen
        int alto   = 700;  // Qué tan alta quieres la imagen
        // ============================================================

        // Cargamos y redimensionamos de forma segura con tus variables
        lblImagenCreditos = new JLabel(redimensionarImagen("Prgcreditos.jpeg", ancho, alto));
        lblImagenCreditos.setBounds(posX, posY, ancho, alto);
        lblFondo.add(lblImagenCreditos);
       
        // BOTÓN PARA VOLVER AL MENÚ 
        ImageIcon iconoOriginalVolver = new ImageIcon(getClass().getResource("salir.png")); 
        Image imagenEscaladaVolver = iconoOriginalVolver.getImage()
                .getScaledInstance(250, 140, Image.SCALE_SMOOTH); // Un poco más pequeño para que sea secundario
        ImageIcon iconoFinalVolver = new ImageIcon(imagenEscaladaVolver);
        JButton botonVolver = new JButton(iconoFinalVolver);
        
        botonVolver.setBounds(950, 680, 250, 140); 
        
        botonVolver.setBorderPainted(false);   
        botonVolver.setContentAreaFilled(false); 
        botonVolver.setFocusPainted(false);    
        botonVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblFondo.add(botonVolver);

        // regresar a la pantalla del menú principal
        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaMenu menu = new VentanaMenu();
                menu.setVisible(true);
                dispose(); // Cierra la ventana de créditos de inmediato
            }
        });
    }

    private ImageIcon redimensionarImagen(String ruta, int ancho, int alto) {
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
        Image imagenOriginal = iconoOriginal.getImage();
        Image imagenRedimensionada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenRedimensionada);
    }
}
