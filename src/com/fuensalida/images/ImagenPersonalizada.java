/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fuensalida.images;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author vPalomo
 */
public class ImagenPersonalizada extends javax.swing.JPanel {
    int x, y;
    String ruta;

    /**
     * 
     * @param jPanel1
     * @param rutaImagen ejemplo "/com/fuensalida/images/deseleccion3.png" 
     */
    public ImagenPersonalizada(JPanel jPanel1, String rutaImagen) {
        this.x = jPanel1.getWidth();
        this.y = jPanel1.getHeight();
        this.ruta=rutaImagen;
        this.setSize(x, y);
    }

    @Override
    public void paint(Graphics g) {
        ImageIcon Img = new ImageIcon(getClass().getResource(this.ruta));
        g.drawImage(Img.getImage(), 0, 0, x, y, null);
    }    

}
