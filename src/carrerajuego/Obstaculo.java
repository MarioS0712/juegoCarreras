/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrerajuego;
import javax.swing.JLabel;
 
public class Obstaculo {
 
    public JLabel label;
    public String tipo;
 
    public Obstaculo(String tipo, JLabel label) {
        this.tipo = tipo;
        this.label = label;
    }
 
    public java.awt.Rectangle getBounds() {
        return label.getBounds();
    }
}
 